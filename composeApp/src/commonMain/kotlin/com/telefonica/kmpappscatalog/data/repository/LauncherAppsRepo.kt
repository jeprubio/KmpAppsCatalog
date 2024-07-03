package com.telefonica.kmpappscatalog.data.repository

import com.telefonica.kmpappscatalog.AppInstallation
import com.telefonica.kmpappscatalog.domain.entities.LauncherApp
import com.telefonica.librarycatalogapi.AppsCatalogApi
import com.telefonica.librarycatalogapi.models.ProductsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class LauncherAppsRepo(
    private val appInstallation: AppInstallation,
) {

    suspend fun getLauncherApps(): Result<List<LauncherApp>> =
        AppsCatalogApi.createDefault().getApps().map { it.toLauncherApp() }

    fun checkAppInstalled(launcherApp: LauncherApp): Flow<Result<Boolean>> {
        return appInstallation.isAppInstalled(launcherApp.androidPackage, launcherApp.iosScheme)
            .map { Result.success(it) }
            .catch { emit(Result.failure(it)) }
    }
}

private fun ProductsResponse.toLauncherApp(): List<LauncherApp> {
    return products.map { (_, product) ->
        LauncherApp(
            name = product.name,
            packageName = product.android.packageName,
            icon = product.icon,
            description = product.description,
            project = product.project,
            androidInstallUrl = product.android.installUrl,
            iosInstallUrl = product.ios.installUrl,
            androidPackage = product.android.packageName,
            iosScheme = product.ios.scheme,
        )
    }
}
