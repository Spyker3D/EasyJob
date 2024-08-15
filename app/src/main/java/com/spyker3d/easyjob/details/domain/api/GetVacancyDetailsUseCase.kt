package com.spyker3d.easyjob.details.domain.api

import kotlinx.coroutines.flow.Flow
import com.spyker3d.easyjob.details.domain.model.VacancyDetails
import com.spyker3d.easyjob.utils.Resource

fun interface GetVacancyDetailsUseCase {
    suspend fun execute(id: String): Flow<Resource<VacancyDetails>>
}
