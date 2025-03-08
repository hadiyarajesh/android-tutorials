package com.hadiyarajesh.compose_in_viewmodel.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.hadiyarajesh.compose_in_viewmodel.ui.theme.AndroidTutorialsTheme
import com.hadiyarajesh.compose_in_viewmodel.vm.WallpaperViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel: WallpaperViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidTutorialsTheme {
                WallpaperScreen(viewModel)
            }
        }
    }
}