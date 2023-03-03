package com.hadiyarajesh.activity_lifecycle_visualizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.hadiyarajesh.activity_lifecycle_visualizer.ui.ActivityLifecycleVisualizerApp
import com.hadiyarajesh.activity_lifecycle_visualizer.utility.showToast

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This will generate a toast message with the current method name.
        showToast(Throwable().stackTrace[0].methodName)

        setContent {
            ActivityLifecycleVisualizerApp()
        }
    }

    override fun onStart() {
        super.onStart()
        showToast(Throwable().stackTrace[0].methodName)
    }

    override fun onResume() {
        super.onResume()
        showToast(Throwable().stackTrace[0].methodName)
    }

    override fun onPause() {
        super.onPause()
        showToast(Throwable().stackTrace[0].methodName)
    }

    override fun onStop() {
        super.onStop()
        showToast(Throwable().stackTrace[0].methodName)
    }

    override fun onDestroy() {
        super.onDestroy()
        showToast(Throwable().stackTrace[0].methodName)
    }
}
