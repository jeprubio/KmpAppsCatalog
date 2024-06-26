package com.telefonica.kmpappscatalog.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.telefonica.kmpappscatalog.AndroidApp
import com.telefonica.kmpappscatalog.data.LocalSettings
import com.telefonica.kmpappscatalog.data.LocalSettings.dataStoreFileName

actual fun createDataStore(): DataStore<Preferences> {
    val context = AndroidApp.INSTANCE
    return LocalSettings.getDataStore(
        producePath = {
            context.filesDir.resolve(dataStoreFileName).absolutePath
        }
    )
}