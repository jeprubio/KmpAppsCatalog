package com.telefonica.kmpappscatalog

import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
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

internal actual fun isAppInstalled(androidPackage: String?, iosScheme: String?): Boolean {
    if (androidPackage == null) return false

    return try {
        val context = AndroidApp.INSTANCE
        context.packageManager.getPackageInfo(androidPackage, PackageManager.GET_ACTIVITIES)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
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
