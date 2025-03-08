package com.hadiyarajesh.compose_in_viewmodel.repository

import com.hadiyarajesh.compose_in_viewmodel.model.Wallpaper
import com.hadiyarajesh.compose_in_viewmodel.util.WallpaperUtility
import javax.inject.Inject

interface WallpaperRepository {
    suspend fun getWallpapers(): List<Wallpaper>
    suspend fun removeWallpaper(index: Int)
}

class WallpaperRepositoryImpl @Inject constructor(
) : WallpaperRepository {
    private val _wallpapers = mutableListOf<Wallpaper>().apply {
        addAll(WallpaperUtility.generateFakeWallpapers(100))
    }

    override suspend fun getWallpapers(): List<Wallpaper> {
        return _wallpapers
    }

    override suspend fun removeWallpaper(index: Int) {
        _wallpapers.removeAt(index)
    }
}
