package com.hadiyarajesh.compose_in_viewmodel.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadiyarajesh.compose_in_viewmodel.repository.WallpaperRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WallpaperViewModel @Inject constructor(
    private val repository: WallpaperRepository
) : ViewModel() {
    private val _wallpapers = MutableStateFlow<WallpaperScreenUiState>(WallpaperScreenUiState.None)
    val wallpapers: StateFlow<WallpaperScreenUiState> get() = _wallpapers

    init {
        getAllWallpapers()
    }

    fun getAllWallpapers() {
        viewModelScope.launch(Dispatchers.IO) {
            _wallpapers.value = WallpaperScreenUiState.Loading
            try {
                val wallpapers = repository.getWallpapers()
                _wallpapers.value = WallpaperScreenUiState.Success(wallpapers)
            } catch (e: Exception) {
                _wallpapers.value = WallpaperScreenUiState.Error(e.message ?: "Unknown error", e)
            }
        }
    }
}
