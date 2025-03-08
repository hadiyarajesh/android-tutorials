package com.hadiyarajesh.compose_in_viewmodel.vm

import androidx.compose.ui.platform.AndroidUiDispatcher
import androidx.lifecycle.ViewModel
import com.hadiyarajesh.compose_in_viewmodel.util.MoleculeCoroutineScope
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel() {
    var cachedMoleculeViewModelScope: MoleculeCoroutineScope? = null

    open val composeMainDispatcher: CoroutineContext
        get() = AndroidUiDispatcher.Main

    override fun onCleared() {
        super.onCleared()
        cachedMoleculeViewModelScope?.scope?.cancel()
    }
}
