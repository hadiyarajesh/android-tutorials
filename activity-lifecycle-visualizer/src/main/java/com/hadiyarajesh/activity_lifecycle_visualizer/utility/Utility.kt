package com.hadiyarajesh.activity_lifecycle_visualizer.utility

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.hadiyarajesh.activity_lifecycle_visualizer.DialogActivity
import com.hadiyarajesh.activity_lifecycle_visualizer.R

const val TAG = "ActivityLifecycleVisualizer"

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun debugLog(msg: String, tr: Throwable? = null) {
    Log.d(TAG, msg, tr)
}

fun startDialogActivity(context: Context) {
    val intent = Intent(context, DialogActivity::class.java)
    context.startActivity(intent)
}

fun startSettingsActivity(context: Context) {
    try {
        context.startActivity(Intent(android.provider.Settings.ACTION_SETTINGS))
    } catch (e: ActivityNotFoundException) {
        debugLog(context.getString(R.string.failed_to_start_settings_activity))
    }
}

fun destroyActivity(context: Context) {
    (context as Activity).finish()
}
