package com.hadiyarajesh.compose_in_viewmodel.vm

import com.hadiyarajesh.compose_in_viewmodel.model.Wallpaper

sealed interface WallpaperUiState {
    data object None : WallpaperUiState

    data object Loading : WallpaperUiState

    data class Success(val wallpapers: List<Wallpaper>) : WallpaperUiState

    data class Error(val message: String, val e: Exception) : WallpaperUiState
}
