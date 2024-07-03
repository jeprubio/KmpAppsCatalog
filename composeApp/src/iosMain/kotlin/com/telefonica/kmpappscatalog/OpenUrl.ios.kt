package com.telefonica.kmpappscatalog

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

const val POLLING_INTERVAL = 1_000L

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
