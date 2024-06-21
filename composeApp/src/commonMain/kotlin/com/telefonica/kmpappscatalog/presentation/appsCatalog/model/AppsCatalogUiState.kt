package com.telefonica.kmpappscatalog.presentation.appsCatalog.model

import com.telefonica.kmpappscatalog.domain.LauncherApp

sealed interface AppsCatalogUiState {
    data object Loading : AppsCatalogUiState
    data class Loaded(val apps: List<LauncherApp>) : AppsCatalogUiState
    data class Error(val error: Throwable) : AppsCatalogUiState
}
