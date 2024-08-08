package com.telefonica.kmpappscatalog.domain.entities

import cafe.adriel.voyager.core.lifecycle.JavaSerializable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.jvm.Transient

data class LauncherApp(
    val name: String,
    val packageName: String,
    val icon: String,
    val description: String,
    val project: String,
    val androidInstallUrl: String,
    val iosInstallUrl: String,
    val androidPackage: String,
    val iosScheme: String,
): JavaSerializable {
    @Transient
    val isInstalled: MutableStateFlow<Boolean> = MutableStateFlow(false)
}
