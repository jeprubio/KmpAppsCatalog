package com.telefonica.kmpappscatalog.di

import com.telefonica.kmpappscatalog.data.repository.DefaultDataStoreRepository
import com.telefonica.kmpappscatalog.data.repository.LauncherAppsRepo
import com.telefonica.kmpappscatalog.domain.repointerfaces.DataStoreRepository
import com.telefonica.kmpappscatalog.domain.usecase.GetLauncherAppsUseCase
import com.telefonica.kmpappscatalog.domain.usecase.GetLayoutTypeUseCase
import com.telefonica.kmpappscatalog.domain.usecase.IsAppInstalledUseCase
import com.telefonica.kmpappscatalog.domain.usecase.SaveLayoutTypeUseCase
import org.koin.dsl.module

fun commonModule() = module {
    factory { LauncherAppsRepo(get()) }
    factory<DataStoreRepository> { DefaultDataStoreRepository(get()) }
    factory { GetLauncherAppsUseCase(get()) }
    factory { IsAppInstalledUseCase(get()) }
    factory { SaveLayoutTypeUseCase(get()) }
    factory { GetLayoutTypeUseCase(get()) }
}
