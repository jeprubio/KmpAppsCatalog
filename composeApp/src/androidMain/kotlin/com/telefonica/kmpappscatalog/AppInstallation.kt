package com.telefonica.kmpappscatalog

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

actual class AppInstallation(
    private val context: Context,
) {
    actual fun isAppInstalled(
        androidPackage: String?,
        iosScheme: String?
    ): Flow<Boolean> {
        if (androidPackage == null) return flow { emit(false) }
        return context.observeAppInstallation(androidPackage).flowOn(Dispatchers.IO)
    }

    actual fun uninstallApp(androidPackage: String?, iosScheme: String?) {
        if (androidPackage == null) return

        val intent = Intent(Intent.ACTION_DELETE).apply {
            data = Uri.parse("package:$androidPackage")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }

    actual fun shouldShowUninstallButton(): Boolean = true

    private fun Context.observeAppInstallation(androidPackage: String): Flow<Boolean> = callbackFlow {
        val receiver = AppInstallReceiver(androidPackage) { isInstalled ->
            trySend(isInstalled)
        }

        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_PACKAGE_ADDED)
            addAction(Intent.ACTION_PACKAGE_REMOVED)
            addDataScheme("package")
        }

        registerReceiver(receiver, filter)
        trySend(isAppInstalled(androidPackage))

        awaitClose {
            unregisterReceiver(receiver)
        }
    }

    private fun Context.isAppInstalled(androidPackage: String): Boolean {
        return try {
            packageManager.getPackageInfo(androidPackage, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}
