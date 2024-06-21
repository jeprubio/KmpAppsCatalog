package com.telefonica.kmpappscatalog.domain

import com.telefonica.kmpappscatalog.data.LauncherAppsRepo

class IsAppInstalledUseCase(private val repo: LauncherAppsRepo) {
    operator fun invoke(launcherApp: LauncherApp): Result<Boolean> = repo.isAppInstalled(launcherApp)
}
