package com.hadiyarajesh.activity_lifecycle_visualizer.utility

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.hadiyarajesh.activity_lifecycle_visualizer.DialogActivity

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
}

fun startDialogActivity(context: Context) {
    val intent = Intent(context, DialogActivity::class.java)
    context.startActivity(intent)
}

fun selectImage(context: Context) {
    val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
        type = "image/*"
    }

    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, "No app found to get image content", Toast.LENGTH_SHORT).show()
    }
}

fun destroyActivity(context: Context) {
    (context as Activity).finish()
}
