package com.spyker3d.easyjob.details.domain.api

import kotlinx.coroutines.flow.Flow
import com.spyker3d.easyjob.details.domain.model.VacancyDetails
import com.spyker3d.easyjob.utils.Resource

interface VacancyDetailsRepository {
    suspend fun getVacancyById(id: String): Flow<Resource<VacancyDetails>>
}
