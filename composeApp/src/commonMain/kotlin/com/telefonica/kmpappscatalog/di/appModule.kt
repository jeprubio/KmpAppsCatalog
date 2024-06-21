package com.telefonica.kmpappscatalog.di

import com.telefonica.kmpappscatalog.data.LauncherAppsRepo
import com.telefonica.kmpappscatalog.domain.GetLauncherAppsUseCase
import com.telefonica.kmpappscatalog.domain.IsAppInstalledUseCase
import org.koin.dsl.module

fun appModule() = module {
    factory { LauncherAppsRepo() }
    factory { GetLauncherAppsUseCase(get()) }
    factory { IsAppInstalledUseCase(get()) }
}
