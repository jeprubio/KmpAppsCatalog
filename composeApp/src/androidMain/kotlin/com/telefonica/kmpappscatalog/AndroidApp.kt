package com.telefonica.kmpappscatalog

import android.app.Application
import android.content.Intent
import android.net.Uri

class AndroidApp : Application() {
    companion object {
        lateinit var INSTANCE: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}

internal actual fun openUrl(androidUrl: String?, iosUrl: String?) {
    val uri = androidUrl?.let { Uri.parse(it) } ?: return
    val intent = Intent().apply {
        action = Intent.ACTION_VIEW
        data = uri
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    AndroidApp.INSTANCE.startActivity(intent)
}
