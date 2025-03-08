package com.hadiyarajesh.compose_in_viewmodel.util

import androidx.compose.runtime.Composable
import app.cash.molecule.RecompositionMode
import app.cash.molecule.launchMolecule
import com.hadiyarajesh.compose_in_viewmodel.vm.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow
import java.io.Closeable
import kotlin.coroutines.CoroutineContext

interface MoleculeClockContextAndType {
    val clockContext: CoroutineContext
    val clockType: RecompositionMode
}

@JvmInline
value class MoleculeCoroutineScope(val scope: CoroutineScope)

context (MoleculeClockContextAndType)
fun <T> MoleculeCoroutineScope.launchMolecule(
    body: @Composable () -> T
): StateFlow<T> {
    return scope.launchMolecule(mode = clockType, body = body)
}

val <T> T.moleculeViewModelScope: MoleculeCoroutineScope where T : BaseViewModel, T : MoleculeClockContextAndType
    get() {
        val scope: MoleculeCoroutineScope? = cachedMoleculeViewModelScope
        if (scope != null) {
            return scope
        }
        cachedMoleculeViewModelScope =
            MoleculeCoroutineScope(CloseableCoroutineScope(SupervisorJob() + composeMainDispatcher + clockContext))
        return cachedMoleculeViewModelScope!!
    }

class CloseableCoroutineScope(context: CoroutineContext) : Closeable, CoroutineScope {
    override val coroutineContext: CoroutineContext = context

    override fun close() {
        coroutineContext.cancel()
    }
}
