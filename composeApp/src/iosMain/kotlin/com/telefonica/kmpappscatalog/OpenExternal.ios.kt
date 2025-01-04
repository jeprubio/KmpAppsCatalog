package com.telefonica.kmpappscatalog

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual class OpenExternal {
    actual fun openUrl(androidUrl: String?, iosUrl: String?) {
        val nsUrl = iosUrl?.let { NSURL.URLWithString(it) } ?: return
        UIApplication.sharedApplication.openURL(nsUrl, options = emptyMap<Any?, Any?>()) { success ->
            if (!success) {
                println("Cannot open URL: $iosUrl")
            }
        }
    }

    actual fun openApp(androidPackage: String?, iosScheme: String?) {
        when {
            iosScheme != null -> {
                NSURL.URLWithString(iosScheme)?.let {
                    UIApplication.sharedApplication.openURL(it, options = emptyMap<Any?, Any?>()) { success ->
                        if (!success) {
                            println("Cannot open app with scheme: $iosScheme")
                        }
                    }
                }
            }
        }
    }
}
