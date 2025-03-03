package com.telefonica.kmpappscatalog.di

import com.telefonica.kmpappscatalog.AppInstallation
import com.telefonica.kmpappscatalog.OpenExternal
import com.telefonica.kmpappscatalog.data.dataStore
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual fun platformModule() = module {
    singleOf(::AppInstallation)
    singleOf(::dataStore)
    singleOf(::OpenExternal)
}
