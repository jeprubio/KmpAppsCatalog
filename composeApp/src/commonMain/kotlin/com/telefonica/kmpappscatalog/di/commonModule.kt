package com.telefonica.kmpappscatalog.di

import com.telefonica.kmpappscatalog.data.repository.DefaultDataStoreRepository
import com.telefonica.kmpappscatalog.data.repository.LauncherAppsRepo
import com.telefonica.kmpappscatalog.domain.repointerfaces.DataStoreRepository
import com.telefonica.kmpappscatalog.domain.usecase.GetLauncherAppsUseCase
import com.telefonica.kmpappscatalog.domain.usecase.GetLayoutTypeUseCase
import com.telefonica.kmpappscatalog.domain.usecase.IsAppInstalledUseCase
import com.telefonica.kmpappscatalog.domain.usecase.SaveLayoutTypeUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun commonModule() = module {
    factoryOf(::LauncherAppsRepo)
    factory<DataStoreRepository> { DefaultDataStoreRepository(get()) }
    factoryOf(::GetLauncherAppsUseCase)
    factoryOf(::IsAppInstalledUseCase)
    factoryOf(::SaveLayoutTypeUseCase)
    factoryOf(::GetLayoutTypeUseCase)
}
