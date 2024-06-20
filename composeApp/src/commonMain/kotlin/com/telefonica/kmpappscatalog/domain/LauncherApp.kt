package com.telefonica.kmpappscatalog.domain

import cafe.adriel.voyager.core.lifecycle.JavaSerializable

data class LauncherApp(
    val name: String,
    val packageName: String,
    val icon: String,
    val description: String,
    val androidInstallUrl: String,
    val iosInstallUrl: String,
): JavaSerializable
