package com.spyker3d.easyjob.favorites.domain.api

import kotlinx.coroutines.flow.Flow
import com.spyker3d.easyjob.details.domain.model.VacancyDetails

interface FavoriteDbInteractor {
    fun isExistsVacancy(vacancyId: Int): Flow<Boolean>

    fun getAllVacancy(): Flow<List<VacancyDetails>>

    fun getAllVacancyByPage(pageNum: Int): Flow<List<VacancyDetails>>

    suspend fun insertVacancy(vacancyDetails: VacancyDetails)

    suspend fun deleteVacancy(vacancyId: Int)

    suspend fun insertVacancyWitCheck(vacancyDetails: VacancyDetails)
}
