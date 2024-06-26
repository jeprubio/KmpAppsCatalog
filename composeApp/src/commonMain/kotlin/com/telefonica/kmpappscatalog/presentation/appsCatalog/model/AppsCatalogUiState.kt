package com.telefonica.kmpappscatalog.presentation.appsCatalog.model

import com.telefonica.kmpappscatalog.domain.entities.LauncherApp
import com.telefonica.kmpappscatalog.presentation.appsCatalog.UILayoutType

data class AppsCatalogUiState(
    val uiLayoutType: UILayoutType = UILayoutType.Grid,
    val catalogDataState: CatalogDataState = CatalogDataState.Loading,
)

sealed interface CatalogDataState {
    data object Loading : CatalogDataState
    data class Loaded(val apps: List<LauncherApp>) : CatalogDataState
    data class Error(val error: Throwable) : CatalogDataState
}
