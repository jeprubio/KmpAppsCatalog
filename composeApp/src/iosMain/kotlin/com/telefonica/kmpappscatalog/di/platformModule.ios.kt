package com.telefonica.kmpappscatalog.di

import com.telefonica.kmpappscatalog.AppInstallation
import com.telefonica.kmpappscatalog.OpenExternal
import com.telefonica.kmpappscatalog.data.DataStoreFactory
import org.koin.dsl.module

actual fun platformModule() = module {
    single { AppInstallation() }
    single { DataStoreFactory().create() }
    single { OpenExternal() }
}
