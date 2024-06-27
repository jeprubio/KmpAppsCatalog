package com.telefonica.kmpappscatalog.domain.entities

import cafe.adriel.voyager.core.lifecycle.JavaSerializable

data class LauncherApp(
    val name: String,
    val packageName: String,
    val icon: String,
    val description: String,
    val androidInstallUrl: String,
    val iosInstallUrl: String,
    val androidPackage: String,
    val iosScheme: String,
): JavaSerializable {
    var isInstalled: Boolean = false
}
