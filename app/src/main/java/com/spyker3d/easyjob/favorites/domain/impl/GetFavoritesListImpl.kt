package com.spyker3d.easyjob.favorites.domain.impl

import kotlinx.coroutines.flow.Flow
import com.spyker3d.easyjob.details.domain.model.VacancyDetails
import com.spyker3d.easyjob.favorites.domain.api.GetFavoritesListUseCase
import com.spyker3d.easyjob.favorites.domain.api.LocalRepository
import com.spyker3d.easyjob.utils.Resource

class GetFavoritesListImpl(private val repository: LocalRepository) : GetFavoritesListUseCase {
    override suspend fun execute(): Flow<Resource<List<VacancyDetails>>> {
        return repository.getFavoritesVacancyList()
    }
}
