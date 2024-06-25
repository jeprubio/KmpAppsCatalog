package com.telefonica.kmpappscatalog.presentation.appsDetails

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.telefonica.kmpappscatalog.domain.IsAppInstalledUseCase
import com.telefonica.kmpappscatalog.domain.LauncherApp
import com.telefonica.kmpappscatalog.presentation.appsDetails.model.AppsDetailsUiState
import com.telefonica.kmpappscatalog.presentation.appsDetails.model.IsAppInstalled
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AppsDetailsScreenViewModel : ScreenModel, KoinComponent {
    private val _uiState = MutableStateFlow(AppsDetailsUiState())
    val uiState = _uiState.asStateFlow()
    private val isAppInstalledUseCase: IsAppInstalledUseCase by inject()

    init {
        Napier.d("AppsDetailsScreenViewModel initialized")
    }

    fun checkAppInstalled(launcherApp: LauncherApp) {
        screenModelScope.launch {
            isAppInstalledUseCase(launcherApp)
                .onStart { _uiState.value = AppsDetailsUiState(isAppInstalled = IsAppInstalled.Loading) }
                .catch { e -> _uiState.value = AppsDetailsUiState(isAppInstalled = IsAppInstalled.Error(e)) }
                .collect { result ->
                    result.fold(
                        onSuccess = { _uiState.value = AppsDetailsUiState(isAppInstalled = IsAppInstalled.Loaded(it)) },
                        onFailure = { _uiState.value = AppsDetailsUiState(isAppInstalled = IsAppInstalled.Error(it)) }
                    )
                }
        }
    }

    override fun onDispose() {
        super.onDispose()
        Napier.d("AppsDetailsScreenViewModel disposed")
    }

}
