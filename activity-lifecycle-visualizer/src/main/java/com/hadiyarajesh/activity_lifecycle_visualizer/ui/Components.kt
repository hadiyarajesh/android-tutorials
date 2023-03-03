package com.hadiyarajesh.activity_lifecycle_visualizer.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ButtonAndInfoView(
    modifier: Modifier = Modifier,
    buttonText: String,
    onButtonClick: () -> Unit,
    onInfoClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = onButtonClick) {
            Text(text = buttonText)
        }
        VerticalSpacer(size = 4)

        IconButton(onClick = onInfoClick) {
            Icon(imageVector = Icons.Default.Info, contentDescription = null)
        }
    }
}

@Composable
fun VerticalSpacer(size: Int) {
    Spacer(modifier = Modifier.width(size.dp))
}
