package com.hadiyarajesh.compose_in_viewmodel.vm.stream

sealed interface WallpaperStreamSignal {
    val showLoading: Boolean

    data object Start : WallpaperStreamSignal {
        override val showLoading: Boolean
            get() = true
    }

    data object Retry : WallpaperStreamSignal {
        override val showLoading: Boolean
            get() = true
    }

    data class WallpaperRemoved(
        val wallpaperId: Int
    ) : WallpaperStreamSignal {
        override val showLoading: Boolean
            get() = true
    }
}
