package com.telefonica.kmpappscatalog.presentation.appsCatalog

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.telefonica.kmpappscatalog.domain.entities.LauncherApp
import com.telefonica.kmpappscatalog.domain.usecase.GetLauncherAppsUseCase
import com.telefonica.kmpappscatalog.domain.usecase.GetLayoutTypeUseCase
import com.telefonica.kmpappscatalog.domain.usecase.IsAppInstalledUseCase
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
    private val isAppInstalledUseCase: IsAppInstalledUseCase by inject()

    init {
        getApps()
        screenModelScope.launch {
            getLayoutTypeUseCase().collect { layoutType ->
                setLayoutType(layoutType.toUILayoutType())
            }
        }
    }

    fun setLayoutType(uiLayoutType: UILayoutType) {
        _uiState.update { it.copy(uiLayoutType = uiLayoutType) }.also {
            screenModelScope.launch {
                saveLayoutTypeUseCase(uiLayoutType.toLayoutType())
            }
        }
    }

    fun setFilter(filter: String) {
        _uiState.update {
            val loadedState = (_uiState.value.catalogDataState as? CatalogDataState.Loaded)
            it.copy(
                selectedFilter = filter,
                catalogDataState = CatalogDataState.Loaded(
                    apps = loadedState?.apps ?: emptyList(),
                    filters = loadedState?.filters ?: emptyList(),
                    filteredApps = getFilteredApps(loadedState?.apps ?: emptyList(), filter)
                )
            )
        }
    }

    private fun getApps() {
        screenModelScope.launch {
            getLauncherUseCase().fold(
                onSuccess = { apps ->
                    val filters: List<String> = getFilters(apps)
                    _uiState.update {
                        it.copy(
                            catalogDataState = CatalogDataState.Loaded(
                                apps,
                                filters,
                                getFilteredApps(apps, (_uiState.value.selectedFilter))
                            )
                        )
                    }
                    checkInstalledApps(apps, filters)
                },
                onFailure = { error ->
                    _uiState.update { it.copy(catalogDataState = CatalogDataState.Error(error)) }
                }
            )
        }
    }

    private fun getFilters(apps: List<LauncherApp>) =
        listOf("All") + apps.map { it.project }.distinct().sorted()

    private fun getFilteredApps(
        apps: List<LauncherApp>,
        selectedFilter: String
    ): List<LauncherApp> {
        val filteredApps =
            if (selectedFilter != "All") apps.filter { it.project == selectedFilter }
            else apps
        return filteredApps
    }

    private fun checkInstalledApps(
        apps: List<LauncherApp>,
        filters: List<String>,
    ) {
        apps.forEach { app ->
            screenModelScope.launch {
                isAppInstalledUseCase(app)
                    .collect { isInstalledResult ->
                        isInstalledResult.fold(
                            onSuccess = { app.isInstalled.value = it },
                            onFailure = { app.isInstalled.value = false }
                        )
                    }
            }
        }
        _uiState.update {
            it.copy(
                catalogDataState = CatalogDataState.Loaded(
                    apps,
                    filters,
                    getFilteredApps(apps, (_uiState.value.selectedFilter)),
                )
            )
        }
    }
}
