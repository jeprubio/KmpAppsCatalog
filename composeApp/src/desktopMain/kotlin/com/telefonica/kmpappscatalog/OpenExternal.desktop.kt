package com.telefonica.kmpappscatalog

actual class OpenExternal {
    actual fun openUrl(androidUrl: String?, iosUrl: String?) {
        /* Do nothing */
    }

    actual fun openApp(androidPackage: String?, iosScheme: String?) {
        /* Do nothing */
    }
}
