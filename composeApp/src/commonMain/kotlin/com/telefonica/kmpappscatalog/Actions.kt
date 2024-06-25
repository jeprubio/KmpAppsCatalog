package com.telefonica.kmpappscatalog

import kotlinx.coroutines.flow.Flow

internal expect fun appInstalled(androidPackage: String?, iosScheme: String?): Flow<Boolean>

internal expect fun openUrl(androidUrl: String?, iosUrl: String?)

internal expect fun openApp(androidPackage: String?, iosScheme: String?)

internal expect fun shouldShowUninstallButton(): Boolean

internal expect fun uninstallApp(androidPackage: String?, iosScheme: String?)
