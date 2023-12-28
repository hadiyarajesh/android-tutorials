package com.hadiyarajesh.activity_lifecycle_visualizer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hadiyarajesh.activity_lifecycle_visualizer.R
import com.hadiyarajesh.activity_lifecycle_visualizer.ui.theme.ActivityLifecycleVisualizerTheme
import com.hadiyarajesh.activity_lifecycle_visualizer.utility.destroyActivity
import com.hadiyarajesh.activity_lifecycle_visualizer.utility.startSettingsActivity
import com.hadiyarajesh.activity_lifecycle_visualizer.utility.startDialogActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityLifecycleVisualizerApp() {
    ActivityLifecycleVisualizerTheme {
        val context = LocalContext.current
        val tooltipState = rememberSaveable { mutableStateOf(false) }
        val tooltipText = rememberSaveable { mutableStateOf("") }

        Scaffold(
            topBar = { TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) }) }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ButtonAndInfoView(
                        modifier = Modifier.fillMaxWidth(),
                        buttonText = stringResource(R.string.trigger_on_pause),
                        onButtonClick = { startDialogActivity(context) },
                        onInfoClick = {
                            tooltipState.value = true
                            tooltipText.value =
                                context.getString(R.string.trigger_on_pause_message)
                        }
                    )

                    ButtonAndInfoView(
                        modifier = Modifier.fillMaxWidth(),
                        buttonText = stringResource(R.string.trigger_on_stop),
                        onButtonClick = { startSettingsActivity(context) },
                        onInfoClick = {
                            tooltipState.value = true
                            tooltipText.value =
                                context.getString(R.string.trigger_on_stop_message)
                        }
                    )

                    ButtonAndInfoView(
                        modifier = Modifier.fillMaxWidth(),
                        buttonText = stringResource(R.string.trigger_on_destroy),
                        onButtonClick = { destroyActivity(context) },
                        onInfoClick = {
                            tooltipState.value = true
                            tooltipText.value =
                                context.getString(R.string.trigger_on_destroy_message)
                        }
                    )

                    TooltipText(
                        expanded = tooltipState,
                        text = tooltipText.value
                    )
                }

                FooterInfoView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun FooterInfoView(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.activity_appear_message),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(text = "")
        Text(
            text = stringResource(R.string.manually_trigger_other_lifecycle_states_message),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
fun ActivityLifecycleVisualizerAppPreview() {
    ActivityLifecycleVisualizerApp()
}
