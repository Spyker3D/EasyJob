package com.spyker3d.easyjob.favorites.domain.api

import kotlinx.coroutines.flow.Flow
import com.spyker3d.easyjob.details.domain.model.VacancyDetails
import com.spyker3d.easyjob.utils.Resource

interface LocalRepository {
    suspend fun getFavoritesVacancyList(): Flow<Resource<List<VacancyDetails>>>
}
