package com.telefonica.kmpappscatalog.domain

import com.telefonica.kmpappscatalog.data.LauncherAppsRepo
import kotlinx.coroutines.flow.Flow

class IsAppInstalledUseCase(private val repo: LauncherAppsRepo) {
    operator fun invoke(launcherApp: LauncherApp): Flow<Result<Boolean>> = repo.checkAppInstalled(launcherApp)
}
