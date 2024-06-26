package com.telefonica.kmpappscatalog.domain.usecase

import com.telefonica.kmpappscatalog.domain.entities.LayoutType
import com.telefonica.kmpappscatalog.domain.repointerfaces.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class GetLayoutTypeUseCase(private val repo: DataStoreRepository) {
    operator fun invoke(): Flow<LayoutType> = repo.getLayoutType()
}
