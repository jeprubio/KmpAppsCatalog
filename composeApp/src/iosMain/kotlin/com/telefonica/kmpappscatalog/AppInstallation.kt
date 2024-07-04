package com.telefonica.kmpappscatalog

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

const val POLLING_INTERVAL = 1000L

actual class AppInstallation {
    actual fun isAppInstalled(
        androidPackage: String?,
        iosScheme: String?
    ): Flow<Boolean> =
        flow {
            while (true) {
                emit(isAppInstalled(iosScheme))
                delay(POLLING_INTERVAL)
            }
        }.flowOn(Dispatchers.IO)

    private fun isAppInstalled(iosScheme: String?): Boolean {
        return when {
            iosScheme != null -> {
                try {
                    val formattedScheme = if (iosScheme.endsWith("://")) iosScheme else "$iosScheme://"
                    NSURL.URLWithString(formattedScheme)?.let {
                        UIApplication.sharedApplication.canOpenURL(it)
                    } ?: false
                } catch (e: Exception) {
                    false
                }
            }

            else -> false
        }
    }

    actual fun uninstallApp(androidPackage: String?, iosScheme: String?) {
        throw (UnsupportedOperationException("Uninstalling apps is not supported on iOS"))
    }

    actual fun shouldShowUninstallButton(): Boolean = false
}
