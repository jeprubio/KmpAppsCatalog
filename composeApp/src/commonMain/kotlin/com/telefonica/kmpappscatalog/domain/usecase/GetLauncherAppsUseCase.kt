package com.telefonica.kmpappscatalog.domain.usecase

import com.telefonica.kmpappscatalog.data.repository.LauncherAppsRepo
import com.telefonica.kmpappscatalog.domain.entities.LauncherApp

class GetLauncherAppsUseCase(private val repo: LauncherAppsRepo) {
    suspend operator fun invoke(): Result<List<LauncherApp>> = repo.getLauncherApps()
}
