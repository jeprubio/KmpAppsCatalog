package com.telefonica.kmpappscatalog.data

import com.telefonica.kmpappscatalog.domain.LauncherApp
import com.telefonica.kmpappscatalog.appInstalled
import com.telefonica.librarycatalogapi.AppsCatalogApi
import com.telefonica.librarycatalogapi.models.ProductsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class LauncherAppsRepo {

    suspend fun getLauncherApps(): Result<List<LauncherApp>> =
        AppsCatalogApi.createDefault().getApps().map { it.toLauncherApp() }

    fun checkAppInstalled(launcherApp: LauncherApp): Flow<Result<Boolean>> {
        return appInstalled(launcherApp.androidPackage, launcherApp.iosScheme).map { Result.success(it) }
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
            androidInstallUrl = product.android.installUrl,
            iosInstallUrl = product.ios.installUrl,
            androidPackage = product.android.packageName,
            iosScheme = product.ios.scheme,
        )
    }
}
