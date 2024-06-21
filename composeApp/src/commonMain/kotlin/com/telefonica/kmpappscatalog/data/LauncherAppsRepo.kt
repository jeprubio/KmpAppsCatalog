package com.telefonica.kmpappscatalog.data

import com.telefonica.kmpappscatalog.domain.LauncherApp
import com.telefonica.kmpappscatalog.isAppInstalled
import com.telefonica.librarycatalogapi.AppsCatalogApi
import com.telefonica.librarycatalogapi.models.ProductsResponse

class LauncherAppsRepo {

    suspend fun getLauncherApps(): Result<List<LauncherApp>> =
        AppsCatalogApi.createDefault().getApps().map { it.toLauncherApp() }

    fun isAppInstalled(launcherApp: LauncherApp): Result<Boolean> {
        return try {
            isAppInstalled(launcherApp.androidPackage, launcherApp.iosScheme).let { Result.success(it) }
        } catch (e: Exception) {
            Result.failure(e)
        }
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
