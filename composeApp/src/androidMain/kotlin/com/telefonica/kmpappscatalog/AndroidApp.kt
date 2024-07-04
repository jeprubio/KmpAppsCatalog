package com.telefonica.kmpappscatalog

import android.app.Application
import com.telefonica.kmpappscatalog.di.initKoin
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext

class AndroidApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@AndroidApp)
        }
        Napier.base(DebugAntilog())
    }
}
