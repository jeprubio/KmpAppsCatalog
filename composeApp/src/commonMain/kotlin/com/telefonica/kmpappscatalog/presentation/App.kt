package com.telefonica.kmpappscatalog.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import com.telefonica.kmpappscatalog.di.appModule
import com.telefonica.kmpappscatalog.presentation.theme.AppTheme
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.koin.core.context.startKoin

@Composable
internal fun App(
    systemAppearance: (isLight: Boolean) -> Unit = {}
) = AppTheme(systemAppearance) {

    startKoin {
        modules(appModule())
    }

    Column(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)) {
        val appsCatalogScreenViewModel =
            getViewModel(Unit, viewModelFactory { AppsCatalogScreenViewModel() })
        Navigator(AppsCatalog(appsCatalogScreenViewModel))
    }
}
