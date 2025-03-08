package com.hadiyarajesh.compose_in_viewmodel.vm.stream

import com.hadiyarajesh.compose_in_viewmodel.model.Wallpaper

data class WallpaperStreamData(
    val wallpapers: List<Wallpaper>,
    val lastAppliedSignal: WallpaperStreamSignal
)
