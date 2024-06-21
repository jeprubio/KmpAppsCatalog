package com.telefonica.kmpappscatalog

internal expect fun isAppInstalled(androidPackage: String?, iosScheme: String?): Boolean

internal expect fun openUrl(androidUrl: String?, iosUrl: String?)

internal expect fun openApp(androidPackage: String?, iosScheme: String?)

internal expect fun shouldShowUninstallButton(): Boolean

internal expect fun uninstallApp(androidPackage: String?, iosScheme: String?)
