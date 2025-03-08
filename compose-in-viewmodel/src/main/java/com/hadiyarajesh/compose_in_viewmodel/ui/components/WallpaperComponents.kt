package com.hadiyarajesh.compose_in_viewmodel.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
                modifier = Modifier.size(150.dp),
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
    val imageShape = RoundedCornerShape(8.dp)

    var wallpaperLoaded by rememberSaveable { mutableStateOf(false) }
    var imageModifier by remember { mutableStateOf(Modifier.fillMaxSize()) }

    val loadingOrErrorModifier = remember {
        Modifier
            .fillMaxSize()
            .border(1.dp, Color.LightGray, imageShape)
    }

    Box(modifier = modifier.clip(imageShape)) {
        AsyncImage(
            modifier = imageModifier,
            model = wallpaper.url,
            placeholder = painterResource(id = R.drawable.ic_placeholder),
            error = painterResource(id = R.drawable.ic_error),
            contentDescription = wallpaper.title,
            contentScale = ContentScale.Crop,
            onLoading = { imageModifier = loadingOrErrorModifier },
            onError = { imageModifier = loadingOrErrorModifier },
            onSuccess = {
                imageModifier = Modifier.fillMaxSize()
                wallpaperLoaded = true
            }
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
