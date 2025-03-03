package com.telefonica.kmpappscatalog

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

actual class AppInstallation {
    actual fun isAppInstalled(
        androidPackage: String?,
        iosScheme: String?
    ): Flow<Boolean> = flowOf(false)

    actual fun uninstallApp(androidPackage: String?, iosScheme: String?) {
        /* Do nothing */
    }

    actual fun shouldShowUninstallButton(): Boolean = false
}
