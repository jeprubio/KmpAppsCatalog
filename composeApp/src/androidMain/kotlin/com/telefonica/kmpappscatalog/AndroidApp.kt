package com.telefonica.kmpappscatalog

import android.app.Application
import android.content.Intent
import android.net.Uri
import com.telefonica.kmpappscatalog.di.initKoin
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext

class AndroidApp : Application() {
    companion object {
        lateinit var INSTANCE: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@AndroidApp)
        }
        Napier.base(DebugAntilog())
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

internal actual fun openApp(androidPackage: String?, iosScheme: String?) {
    if (androidPackage == null) return

    val context = AndroidApp.INSTANCE
    val intent = context.packageManager.getLaunchIntentForPackage(androidPackage)
    intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
}
