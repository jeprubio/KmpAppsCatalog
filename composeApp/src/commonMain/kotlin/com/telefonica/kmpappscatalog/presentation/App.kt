package com.telefonica.kmpappscatalog.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.telefonica.kmpappscatalog.di.appModule
import com.telefonica.kmpappscatalog.presentation.appsCatalog.AppsCatalog
import com.telefonica.kmpappscatalog.presentation.theme.AppTheme
import org.koin.core.context.startKoin

@Composable
internal fun App(
    systemAppearance: (isLight: Boolean) -> Unit = {}
) {
    startKoin {
        modules(appModule())
    }

    AppTheme(systemAppearance) {
        Column(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)) {
            Navigator(AppsCatalog()) {
                SlideTransition(it)
            }
        }
    }
}
