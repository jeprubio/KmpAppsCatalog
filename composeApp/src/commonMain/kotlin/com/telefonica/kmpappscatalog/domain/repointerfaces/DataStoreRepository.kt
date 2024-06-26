package com.telefonica.kmpappscatalog.domain.repointerfaces

import com.telefonica.kmpappscatalog.domain.entities.LayoutType
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveLayoutType(layoutType: LayoutType): Boolean

    fun getLayoutType(): Flow<LayoutType>
}
