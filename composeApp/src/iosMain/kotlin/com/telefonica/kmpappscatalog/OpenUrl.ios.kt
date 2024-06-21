package com.telefonica.kmpappscatalog

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

internal actual fun isAppInstalled(androidPackage: String?, iosScheme: String?): Boolean {
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
