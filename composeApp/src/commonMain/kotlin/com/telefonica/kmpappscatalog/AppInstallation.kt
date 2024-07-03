package com.telefonica.kmpappscatalog

import kotlinx.coroutines.flow.Flow

expect class AppInstallation {
    fun isAppInstalled(androidPackage: String?, iosScheme: String?): Flow<Boolean>
    fun uninstallApp(androidPackage: String?, iosScheme: String?)
    fun shouldShowUninstallButton(): Boolean
}
