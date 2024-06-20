package com.telefonica.kmpappscatalog.domain

import com.telefonica.kmpappscatalog.data.LauncherAppsRepo

class GetLauncherAppsUseCase(private val repo: LauncherAppsRepo) {
    suspend operator fun invoke(): Result<List<LauncherApp>> = repo.getLauncherApps()
}
