package com.hadiyarajesh.compose_in_viewmodel.di

import app.cash.molecule.AndroidUiDispatcher
import app.cash.molecule.RecompositionMode
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
class MoleculeModule {
    @MoleculeContextClock
    @Provides
    @Singleton
    fun provideContextClock(): CoroutineContext {
        return AndroidUiDispatcher.Main
    }

    @MoleculeRecompositionMode
    @Provides
    @Singleton
    fun provideClockType(): RecompositionMode {
        return RecompositionMode.ContextClock
    }
}
