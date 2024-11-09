package com.telefonica.kmpappscatalog.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideOrientation
import cafe.adriel.voyager.transitions.SlideTransition
import com.telefonica.kmpappscatalog.presentation.appsCatalog.AppsCatalog
import com.telefonica.kmpappscatalog.presentation.theme.AppTheme

@Composable
internal fun App(
    systemAppearance: (isLight: Boolean) -> Unit = {}
) {
    AppTheme(systemAppearance) {
        Column(modifier = Modifier.fillMaxSize().safeDrawingPadding()) {
            Navigator(AppsCatalog()) {
                SlideTransition(it, orientation = SlideOrientation.Vertical)
            }
        }
    }
}
