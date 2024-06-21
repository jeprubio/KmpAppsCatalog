package com.telefonica.kmpappscatalog.presentation.appsDetails.model

data class AppsDetailsUiState(
    val isAppInstalled: IsAppInstalled = IsAppInstalled.Loading,
)

sealed interface IsAppInstalled {
    data object Loading : IsAppInstalled
    data class Loaded(val isInstalled: Boolean) : IsAppInstalled
    data class Error(val error: Throwable) : IsAppInstalled
}
