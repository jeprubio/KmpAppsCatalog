package com.telefonica.kmpappscatalog

import android.content.Context
import android.content.Intent
import android.net.Uri

actual class OpenExternal(
    private val context: Context,
) {
    actual fun openUrl(androidUrl: String?, iosUrl: String?) {
        val uri = androidUrl?.let { Uri.parse(it) } ?: return
        val intent = Intent().apply {
            action = Intent.ACTION_VIEW
            data = uri
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }

    actual fun openApp(androidPackage: String?, iosScheme: String?) {
        if (androidPackage == null) return

        val intent = context.packageManager.getLaunchIntentForPackage(androidPackage)
        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}
