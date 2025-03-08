package com.hadiyarajesh.compose_in_viewmodel.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
@MustBeDocumented
annotation class MoleculeContextClock

@Qualifier
@Retention(AnnotationRetention.BINARY)
@MustBeDocumented
annotation class MoleculeRecompositionMode
