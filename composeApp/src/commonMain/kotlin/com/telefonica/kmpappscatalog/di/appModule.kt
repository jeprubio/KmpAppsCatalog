package com.telefonica.kmpappscatalog.di

import com.telefonica.kmpappscatalog.data.LauncherAppsRepo
import com.telefonica.kmpappscatalog.domain.GetLauncherAppsUseCase
import org.koin.dsl.module

fun appModule() = module {
    factory { GetLauncherAppsUseCase(get()) }
    factory { LauncherAppsRepo() }
}
