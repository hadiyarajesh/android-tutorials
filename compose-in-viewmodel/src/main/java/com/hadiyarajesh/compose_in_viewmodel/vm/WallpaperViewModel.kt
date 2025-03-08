package com.hadiyarajesh.compose_in_viewmodel.vm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import app.cash.molecule.RecompositionMode
import com.hadiyarajesh.compose_in_viewmodel.di.MoleculeContextClock
import com.hadiyarajesh.compose_in_viewmodel.di.MoleculeRecompositionMode
import com.hadiyarajesh.compose_in_viewmodel.model.Wallpaper
import com.hadiyarajesh.compose_in_viewmodel.repository.WallpaperRepository
import com.hadiyarajesh.compose_in_viewmodel.util.MoleculeClockContextAndType
import com.hadiyarajesh.compose_in_viewmodel.util.launchMolecule
import com.hadiyarajesh.compose_in_viewmodel.util.moleculeViewModelScope
import com.hadiyarajesh.compose_in_viewmodel.vm.stream.WallpaperStreamData
import com.hadiyarajesh.compose_in_viewmodel.vm.stream.WallpaperStreamSignal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class WallpaperViewModel @Inject constructor(
    private val repository: WallpaperRepository,
    @MoleculeContextClock override val clockContext: CoroutineContext,
    @MoleculeRecompositionMode override val clockType: RecompositionMode
) : MoleculeClockContextAndType,
    BaseViewModel() {
    private val _wallpaperState = MutableStateFlow<WallpaperUiState>(WallpaperUiState.None)
    val wallpaperState: StateFlow<WallpaperUiState> get() = _wallpaperState.asStateFlow()

    private val streamSignalFlow = MutableSharedFlow<WallpaperStreamSignal>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        start()
    }

    fun start() {
        viewModelScope.launch(Dispatchers.IO) {
            moleculeViewModelScope.launchMolecule {
                setupDataStream()
            }.collect { uiState ->
                _wallpaperState.value = uiState
            }
        }
    }

    @Composable
    fun setupDataStream(): WallpaperUiState {
        var currentState: WallpaperUiState by remember { mutableStateOf(WallpaperUiState.Loading) }

        fun emitState(newState: WallpaperUiState) {
            currentState = newState
        }

        val streamDataResultFromState by produceState<WallpaperStreamData?>(initialValue = null) {
            streamSignalFlow
                .onSubscription { emit(WallpaperStreamSignal.Start) }
                .collect { currentStreamSignal ->
                    if (currentStreamSignal.showLoading) {
                        emitState(WallpaperUiState.Loading)
                    }

                    when (currentStreamSignal) {
                        WallpaperStreamSignal.Start -> {
                            val updatedStreamData = handleStartAndRefresh(currentStreamSignal)
                            value = updatedStreamData
                        }

                        WallpaperStreamSignal.Retry -> {
                            value = handleStartAndRefresh(currentStreamSignal)
                        }

                        is WallpaperStreamSignal.WallpaperRemoved -> {
                            value?.let {
                                val updatedStreamData = handleWallpaperRemoved(
                                    currentStreamSignal.wallpaperId,
                                    it,
                                    currentStreamSignal
                                )
                                value = updatedStreamData
                            }
                        }
                    }
                }
        }

        LaunchedEffect(streamDataResultFromState) {
            val currentStreamData = streamDataResultFromState

            if (currentStreamData != null) {
                val newState = WallpaperUiState.Success(currentStreamData.wallpapers)
                emitState(newState)
            }
        }

        return currentState
    }

    private suspend fun handleStartAndRefresh(
        signal: WallpaperStreamSignal
    ): WallpaperStreamData {
        val wallpapers = getAllWallpapers()

        return WallpaperStreamData(
            wallpapers = wallpapers,
            lastAppliedSignal = signal
        )
    }

    private suspend fun getAllWallpapers(): List<Wallpaper> {
        return repository.getWallpapers()
    }

    fun onRemoveWallpaperClicked(index: Int) {
        val wallpaper = (_wallpaperState.value as WallpaperUiState.Success).wallpapers[index]
        streamSignalFlow.tryEmit(WallpaperStreamSignal.WallpaperRemoved(wallpaper.id))
    }

    fun onRetryClick() {
        streamSignalFlow.tryEmit(WallpaperStreamSignal.Retry)
    }

    private suspend fun handleWallpaperRemoved(
        wallpaperId: Int,
        streamData: WallpaperStreamData,
        signal: WallpaperStreamSignal
    ): WallpaperStreamData {
        repository.removeWallpaper(wallpaperId)
        val updatedWallpapers = repository.getWallpapers()
        return streamData.copy(wallpapers = updatedWallpapers, lastAppliedSignal = signal)
    }
}
