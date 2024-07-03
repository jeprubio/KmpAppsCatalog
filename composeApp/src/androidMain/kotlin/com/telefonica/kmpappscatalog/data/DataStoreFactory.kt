package com.telefonica.kmpappscatalog.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.telefonica.kmpappscatalog.data.LocalSettings.dataStoreFileName

actual class DataStoreFactory(
    private val context: Context
) {
    actual fun create(): DataStore<Preferences> {
        return LocalSettings.getDataStore(
            producePath = {
                context.filesDir.resolve(dataStoreFileName).absolutePath
            }
        )
    }
}
