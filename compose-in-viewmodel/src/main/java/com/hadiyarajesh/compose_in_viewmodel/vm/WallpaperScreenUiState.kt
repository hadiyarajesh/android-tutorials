package com.hadiyarajesh.compose_in_viewmodel.vm

import com.hadiyarajesh.compose_in_viewmodel.model.Wallpaper

sealed interface WallpaperScreenUiState {
    data object None : WallpaperScreenUiState

    data object Loading : WallpaperScreenUiState

    data class Success(val wallpapers: List<Wallpaper>) : WallpaperScreenUiState

    data class Error(val message: String, val t: Throwable?) : WallpaperScreenUiState
}
