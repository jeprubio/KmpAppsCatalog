package com.telefonica.kmpappscatalog

expect class OpenExternal {
    fun openUrl(androidUrl: String?, iosUrl: String?)
    fun openApp(androidPackage: String?, iosScheme: String?)
}
