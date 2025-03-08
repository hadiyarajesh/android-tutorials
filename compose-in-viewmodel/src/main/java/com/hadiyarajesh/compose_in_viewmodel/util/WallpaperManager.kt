package com.hadiyarajesh.compose_in_viewmodel.util

import com.hadiyarajesh.compose_in_viewmodel.model.Wallpaper
import javax.inject.Inject

class WallpaperManager @Inject constructor() {
    fun generateWallpapers(count: Int): List<Wallpaper> {
        return List(count) { index ->
            Wallpaper(
                id = (index + 1),
                title = "Wallpaper ${index + 1}",
                url = "https://picsum.photos/id/${index + 1}/800/600"
            )
        }.shuffled()
    }
}
