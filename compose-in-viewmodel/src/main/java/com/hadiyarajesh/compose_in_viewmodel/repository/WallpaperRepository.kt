package com.hadiyarajesh.compose_in_viewmodel.repository

import com.hadiyarajesh.compose_in_viewmodel.model.Wallpaper
import com.hadiyarajesh.compose_in_viewmodel.util.WallpaperUtility
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

interface WallpaperRepository {
    suspend fun getWallpapers(): List<Wallpaper>
    suspend fun removeWallpaper(id: Int)
}

class WallpaperRepositoryImpl @Inject constructor(
) : WallpaperRepository {
    private val _wallpapers = mutableListOf<Wallpaper>().apply {
        addAll(WallpaperUtility.generateFakeWallpapers(100))
    }

    override suspend fun getWallpapers(): List<Wallpaper> {
        delay(5.seconds) // Simulate network request
        return _wallpapers
    }

    override suspend fun removeWallpaper(id: Int) {
        _wallpapers.removeIf { it.id == id }
    }
}
