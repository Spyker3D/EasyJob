package com.spyker3d.easyjob.details.domain.impl

import kotlinx.coroutines.flow.Flow
import com.spyker3d.easyjob.search.domain.api.VacancyRepository
import com.spyker3d.easyjob.details.domain.api.DetailsDbInteractor
import com.spyker3d.easyjob.details.domain.model.VacancyDetails

class DetailsDbInteractorImpl(private val vacancyRepository: VacancyRepository) :
    DetailsDbInteractor {
    override suspend fun deleteVacancy(vacancyId: Int) {
        vacancyRepository.deleteVacancy(vacancyId)
    }

    override suspend fun insertVacancy(vacancyDetails: VacancyDetails) {
        vacancyRepository.insertVacancy(vacancyDetails)
    }

    override suspend fun insertVacancyWithCheck(vacancyDetails: VacancyDetails) {
        vacancyRepository.insertVacancyWithCheck(vacancyDetails)
    }

    override fun isExistsVacancy(vacancyId: Int): Flow<Boolean> {
        return vacancyRepository.isExistsVacancy(vacancyId)
    }

    override fun getVacancyById(vacancyId: Int): Flow<VacancyDetails?> {
        return vacancyRepository.getVacancyById(vacancyId)
    }
}
