package com.telefonica.kmpappscatalog.di

import com.telefonica.kmpappscatalog.AppInstallation
import org.koin.dsl.module

actual fun platformModule() = module {
    single { AppInstallation() }
}
