package com.telefonica.kmpappscatalog.presentation.appsDetails

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.telefonica.kmpappscatalog.domain.IsAppInstalledUseCase
import com.telefonica.kmpappscatalog.domain.LauncherApp
import com.telefonica.kmpappscatalog.presentation.appsDetails.model.AppsDetailsUiState
import com.telefonica.kmpappscatalog.presentation.appsDetails.model.IsAppInstalled
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AppsDetailsScreenViewModel : ScreenModel, KoinComponent {
    private val _uiState = MutableStateFlow(AppsDetailsUiState())
    val uiState = _uiState.asStateFlow()
    private val isAppInstalledUseCase: IsAppInstalledUseCase by inject()

    fun checkAppInstalled(launcherApp: LauncherApp) {
        screenModelScope.launch {
            isAppInstalledUseCase(launcherApp).fold(
                onSuccess = { result ->
                    _uiState.update { it.copy(isAppInstalled = IsAppInstalled.Loaded(result)) }
                },
                onFailure = { error ->
                    _uiState.update { it.copy(isAppInstalled = IsAppInstalled.Error(error)) }
                }
            )
        }
    }

}
