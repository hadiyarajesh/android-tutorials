package com.hadiyarajesh.compose_in_viewmodel.repository

import com.hadiyarajesh.compose_in_viewmodel.model.Wallpaper
import com.hadiyarajesh.compose_in_viewmodel.util.WallpaperManager
import javax.inject.Inject

interface WallpaperRepository {
    suspend fun getWallpapers(): List<Wallpaper>
    suspend fun removeWallpaper(wallpaperId: Int)
}

class WallpaperRepositoryImpl @Inject constructor(
    private val wallpaperManager: WallpaperManager
) : WallpaperRepository {
    private val _defaultWallpapers = mutableListOf<Wallpaper>().apply {
        addAll(wallpaperManager.generateWallpapers(100))
    }

    override suspend fun getWallpapers(): List<Wallpaper> {
        return _defaultWallpapers
    }

    override suspend fun removeWallpaper(wallpaperId: Int) {
        _defaultWallpapers.removeIf { it.id == wallpaperId }
    }
}
