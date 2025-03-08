package com.hadiyarajesh.compose_in_viewmodel.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hadiyarajesh.compose_in_viewmodel.ui.components.ErrorView
import com.hadiyarajesh.compose_in_viewmodel.ui.components.LoadingIndicator
import com.hadiyarajesh.compose_in_viewmodel.ui.components.WallpaperList
import com.hadiyarajesh.compose_in_viewmodel.vm.WallpaperUiState
import com.hadiyarajesh.compose_in_viewmodel.vm.WallpaperViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WallpaperScreen(
    viewModel: WallpaperViewModel
) {
    val wallpaperUiState by viewModel.wallpaperState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "ComposeInViewModel App") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            WallpaperUi(
                uiState = wallpaperUiState,
                onRemoveWallpaperClick = { index ->
                    viewModel.onRemoveWallpaperClicked(index)
                },
                onRetryClick = {
                    viewModel.onRetryClick()
                }
            )
        }
    }
}

@Composable
private fun WallpaperUi(
    uiState: WallpaperUiState,
    onRemoveWallpaperClick: (index: Int) -> Unit,
    onRetryClick: () -> Unit
) {
    when (uiState) {
        WallpaperUiState.None -> {}

        WallpaperUiState.Loading -> {
            LoadingIndicator(modifier = Modifier.fillMaxSize())
        }

        is WallpaperUiState.Success -> {
            val wallpapers = uiState.wallpapers

            WallpaperList(
                modifier = Modifier.fillMaxSize(),
                wallpapers = wallpapers,
                onDeleteClick = { index -> onRemoveWallpaperClick(index) }
            )
        }

        is WallpaperUiState.Error -> {
            ErrorView(
                modifier = Modifier.fillMaxSize(),
                message = uiState.message,
                onRetryClick = onRetryClick
            )
        }
    }
}
