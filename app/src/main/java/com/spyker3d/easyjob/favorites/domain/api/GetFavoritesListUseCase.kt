package com.spyker3d.easyjob.favorites.domain.api

import kotlinx.coroutines.flow.Flow
import com.spyker3d.easyjob.details.domain.model.VacancyDetails
import com.spyker3d.easyjob.utils.Resource

fun interface GetFavoritesListUseCase {
    suspend fun execute(): Flow<Resource<List<VacancyDetails>>>
}
