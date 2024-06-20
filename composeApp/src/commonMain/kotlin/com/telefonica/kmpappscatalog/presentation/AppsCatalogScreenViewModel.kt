package com.telefonica.kmpappscatalog.presentation

import com.telefonica.kmpappscatalog.domain.GetLauncherAppsUseCase
import com.telefonica.kmpappscatalog.presentation.model.AppsCatalogUiState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AppsCatalogScreenViewModel : ViewModel(), KoinComponent {
    private val _uiState = MutableStateFlow<AppsCatalogUiState>(AppsCatalogUiState.Loading)
    val uiState = _uiState.asStateFlow()
    private val getLauncherUseCase: GetLauncherAppsUseCase by inject()

    init {
        getApps()
    }

    private fun getApps() {
        viewModelScope.launch {
            getLauncherUseCase().fold(
                onSuccess = { apps ->
                    _uiState.update { AppsCatalogUiState.Loaded(apps) }
                },
                onFailure = { error ->
                    _uiState.update { AppsCatalogUiState.Error(error) }
                }
            )
        }
    }
}
