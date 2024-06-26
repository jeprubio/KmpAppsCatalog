package com.telefonica.kmpappscatalog.domain.usecase

import com.telefonica.kmpappscatalog.data.repository.LauncherAppsRepo
import com.telefonica.kmpappscatalog.domain.entities.LauncherApp
import kotlinx.coroutines.flow.Flow

class IsAppInstalledUseCase(private val repo: LauncherAppsRepo) {
    operator fun invoke(launcherApp: LauncherApp): Flow<Result<Boolean>> = repo.checkAppInstalled(launcherApp)
}
