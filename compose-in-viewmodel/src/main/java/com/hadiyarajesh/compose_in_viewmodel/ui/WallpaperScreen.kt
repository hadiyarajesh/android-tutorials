package com.hadiyarajesh.compose_in_viewmodel.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.hadiyarajesh.compose_in_viewmodel.R
import com.hadiyarajesh.compose_in_viewmodel.model.Wallpaper
import com.hadiyarajesh.compose_in_viewmodel.util.ErrorView
import com.hadiyarajesh.compose_in_viewmodel.util.LoadingIndicator
import com.hadiyarajesh.compose_in_viewmodel.vm.WallpaperScreenUiState
import com.hadiyarajesh.compose_in_viewmodel.vm.WallpaperViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WallpaperScreen(
    viewModel: WallpaperViewModel
) {
    val wallpaperUiState by viewModel.wallpapers.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "ComposeInViewModel App") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (wallpaperUiState) {
                WallpaperScreenUiState.None -> {}

                WallpaperScreenUiState.Loading -> {
                    LoadingIndicator(modifier = Modifier.fillMaxSize())
                }

                is WallpaperScreenUiState.Success -> {
                    val wallpapers = (wallpaperUiState as WallpaperScreenUiState.Success).wallpapers
                    WallpaperList(
                        modifier = Modifier.fillMaxSize(),
                        wallpapers = wallpapers,
                        onDeleteClick = { index ->
                            viewModel.removeWallpaper(index)
                        }
                    )
                }

                is WallpaperScreenUiState.Error -> {
                    val message = (wallpaperUiState as WallpaperScreenUiState.Error).message
                    ErrorView(
                        modifier = Modifier.fillMaxSize(),
                        message = message,
                        onRetryClick = { }
                    )
                }
            }
        }
    }
}

@Composable
fun WallpaperList(
    modifier: Modifier = Modifier,
    wallpapers: List<Wallpaper>,
    onDeleteClick: (index: Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        itemsIndexed(
            items = wallpapers,
            key = { index, wallpaper -> wallpaper.id }
        ) { index, wallpaper ->
            WallpaperItem(
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(8.dp)),
                wallpaper = wallpaper,
                onDeleteClick = { onDeleteClick(index) }
            )
        }
    }
}

@Composable
private fun WallpaperItem(
    modifier: Modifier = Modifier,
    wallpaper: Wallpaper,
    onDeleteClick: (Wallpaper) -> Unit
) {
    var wallpaperLoaded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = wallpaper.url,
            placeholder = painterResource(id = R.drawable.placeholder),
            contentDescription = wallpaper.title,
            contentScale = ContentScale.Crop,
            onSuccess = { wallpaperLoaded = true }
        )

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.TopEnd),
            enter = fadeIn() + slideInHorizontally { it },
            exit = fadeOut() + slideOutHorizontally { it },
            visible = wallpaperLoaded
        ) {
            IconButton(onClick = { onDeleteClick(wallpaper) }) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.White
                    )
                }
            }
        }
    }
}
