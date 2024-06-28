package com.telefonica.kmpappscatalog

import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import com.telefonica.kmpappscatalog.presentation.initKoin
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

const val POLLING_INTERVAL = 1_000L

class AndroidApp : Application() {
    companion object {
        lateinit var INSTANCE: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()

        initKoin()
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

internal actual fun shouldShowUninstallButton(): Boolean {
    return true
}

internal actual fun uninstallApp(androidPackage: String?, iosScheme: String?) {
    if (androidPackage == null) return

    val context = AndroidApp.INSTANCE
    val intent = Intent(Intent.ACTION_DELETE).apply {
        data = Uri.parse("package:$androidPackage")
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(intent)
}
