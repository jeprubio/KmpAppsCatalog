package com.telefonica.kmpappscatalog

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.telefonica.kmpappscatalog.presentation.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Hot Reload",
    ) {
        App()
    }
}
