package com.telefonica.kmpappscatalog.domain.usecase

import com.telefonica.kmpappscatalog.domain.entities.LayoutType
import com.telefonica.kmpappscatalog.domain.repointerfaces.DataStoreRepository

class SaveLayoutTypeUseCase(private val repo: DataStoreRepository) {
    suspend operator fun invoke(layoutType: LayoutType): Boolean = repo.saveLayoutType(layoutType)
}
