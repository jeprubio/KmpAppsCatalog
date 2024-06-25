package com.telefonica.kmpappscatalog

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

const val POLLING_INTERVAL = 1_000L

internal actual fun appInstalled(androidPackage: String?, iosScheme: String?): Flow<Boolean> =
    flow {
        while (true) {
            emit(isAppInstalled(iosScheme))
            delay(POLLING_INTERVAL)
        }
    }.flowOn(Dispatchers.IO)

private fun isAppInstalled(iosScheme: String?): Boolean {
    return when {
        iosScheme != null -> {
            NSURL.URLWithString(iosScheme)?.let {
                UIApplication.sharedApplication.canOpenURL(it)
            } ?: false
        }

        else -> false
    }
}

internal actual fun openUrl(androidUrl: String?, iosUrl: String?) {
    val nsUrl = iosUrl?.let { NSURL.URLWithString(it) } ?: return
    UIApplication.sharedApplication.openURL(nsUrl)
}

internal actual fun openApp(androidPackage: String?, iosScheme: String?) {
    when {
        iosScheme != null -> {
            NSURL.URLWithString(iosScheme)?.let {
                UIApplication.sharedApplication.openURL(it)
            }
        }
    }
}

internal actual fun shouldShowUninstallButton(): Boolean {
    return false
}

internal actual fun uninstallApp(androidPackage: String?, iosScheme: String?) {
    throw (UnsupportedOperationException("Uninstalling apps is not supported on iOS"))
}
