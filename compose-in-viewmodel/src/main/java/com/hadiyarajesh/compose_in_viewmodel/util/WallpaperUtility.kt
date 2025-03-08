package com.hadiyarajesh.compose_in_viewmodel.util

import com.hadiyarajesh.compose_in_viewmodel.model.Wallpaper

object WallpaperUtility {
    fun generateFakeWallpapers(count: Int): List<Wallpaper> {
        return List(count) { index ->
            Wallpaper(
                id = (index + 1),
                title = "Wallpaper ${index + 1}",
                url = "https://picsum.photos/id/${index + 1}/200/300"
            )
        }
    }
}