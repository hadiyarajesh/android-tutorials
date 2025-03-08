package com.hadiyarajesh.compose_in_viewmodel.di

import com.hadiyarajesh.compose_in_viewmodel.repository.WallpaperRepository
import com.hadiyarajesh.compose_in_viewmodel.repository.WallpaperRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideWallpaperRepository(
        impl: WallpaperRepositoryImpl
    ): WallpaperRepository
}
