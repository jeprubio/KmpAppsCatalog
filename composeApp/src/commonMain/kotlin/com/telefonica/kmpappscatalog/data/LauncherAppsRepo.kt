package com.telefonica.kmpappscatalog.data

import com.telefonica.kmpappscatalog.domain.LauncherApp
import com.telefonica.librarycatalogapi.AppsCatalogApi
import com.telefonica.librarycatalogapi.models.ProductsResponse

class LauncherAppsRepo {

    suspend fun getLauncherApps(): Result<List<LauncherApp>> =
        AppsCatalogApi.createDefault().getApps().map { it.toLauncherApp() }
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
        )
    }
}
