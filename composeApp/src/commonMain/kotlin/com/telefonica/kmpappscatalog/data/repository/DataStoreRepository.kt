package com.telefonica.kmpappscatalog.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.telefonica.kmpappscatalog.domain.entities.LayoutType
import com.telefonica.kmpappscatalog.domain.repointerfaces.DataStoreRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

class DefaultDataStoreRepository(
    private val dataStore: DataStore<Preferences>,
): DataStoreRepository {
    companion object {
        val LAYOUT_TYPE_KEY = stringPreferencesKey("layout_type")
    }

    override suspend fun saveLayoutType(layoutType: LayoutType): Boolean =
        try {
            dataStore.edit { settings ->
                settings[LAYOUT_TYPE_KEY] = layoutType.name
            }
            true
        } catch (e: Exception) {
            Napier.e { "Error saving layout type: $e" }
            false
        }

    override fun getLayoutType(): Flow<LayoutType> =
        dataStore.data
            .catch { emptyFlow<String>() }
            .map { preferences ->
                val layoutType = preferences[LAYOUT_TYPE_KEY]
                LayoutType.valueOf(layoutType ?: LayoutType.Grid.name)
            }
}
