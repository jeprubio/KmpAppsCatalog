package com.telefonica.kmpappscatalog.presentation.appsCatalog

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.telefonica.kmpappscatalog.domain.usecase.GetLauncherAppsUseCase
import com.telefonica.kmpappscatalog.domain.usecase.GetLayoutTypeUseCase
import com.telefonica.kmpappscatalog.domain.usecase.SaveLayoutTypeUseCase
import com.telefonica.kmpappscatalog.presentation.appsCatalog.model.AppsCatalogUiState
import com.telefonica.kmpappscatalog.presentation.appsCatalog.model.CatalogDataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AppsCatalogScreenViewModel : ScreenModel, KoinComponent {
    private val _uiState = MutableStateFlow(AppsCatalogUiState())
    val uiState = _uiState.asStateFlow()
    private val getLauncherUseCase: GetLauncherAppsUseCase by inject()
    private val saveLayoutTypeUseCase: SaveLayoutTypeUseCase by inject()
    private val getLayoutTypeUseCase: GetLayoutTypeUseCase by inject()

    init {
        getApps()
        screenModelScope.launch {
            getLayoutTypeUseCase().collect { layoutType ->
                setLayoutType(layoutType.toUILayoutType())
            }
        }
    }

    private fun getApps() {
        screenModelScope.launch {
            getLauncherUseCase().fold(
                onSuccess = { apps ->
                    _uiState.update { it.copy(catalogDataState = CatalogDataState.Loaded(apps)) }
                },
                onFailure = { error ->
                    _uiState.update { it.copy(catalogDataState = CatalogDataState.Error(error)) }
                }
            )
        }
    }

    fun setLayoutType(uiLayoutType: UILayoutType) {
        _uiState.update { it.copy(uiLayoutType = uiLayoutType) }.also {
            screenModelScope.launch {
                saveLayoutTypeUseCase(uiLayoutType.toLayoutType())
            }
        }
    }

}
