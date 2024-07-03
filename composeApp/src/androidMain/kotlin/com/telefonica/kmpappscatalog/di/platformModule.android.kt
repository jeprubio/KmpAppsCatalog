package com.telefonica.kmpappscatalog.di

import com.telefonica.kmpappscatalog.AppInstallation
import com.telefonica.kmpappscatalog.data.DataStoreFactory
import org.koin.dsl.module

actual fun platformModule() = module {
    single { AppInstallation(get()) }
    single { DataStoreFactory(get()).create() }
}
