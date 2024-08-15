package com.spyker3d.easyjob.details.domain.api

import kotlinx.coroutines.flow.Flow
import com.spyker3d.easyjob.details.domain.model.VacancyDetails

interface DetailsDbInteractor {
    fun isExistsVacancy(vacancyId: Int): Flow<Boolean>

    suspend fun insertVacancy(vacancyDetails: VacancyDetails)

    suspend fun deleteVacancy(vacancyId: Int)

    suspend fun insertVacancyWithCheck(vacancyDetails: VacancyDetails)

    fun getVacancyById(vacancyId: Int): Flow<VacancyDetails?>
}
