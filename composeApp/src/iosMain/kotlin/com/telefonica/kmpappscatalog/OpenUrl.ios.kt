package com.telefonica.kmpappscatalog

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

internal actual fun openUrl(androidUrl: String?, iosUrl: String?) {
    val nsUrl = iosUrl?.let { NSURL.URLWithString(it) } ?: return
    UIApplication.sharedApplication.openURL(nsUrl)
}
