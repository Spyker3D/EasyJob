package com.spyker3d.easyjob.details.domain.impl

import kotlinx.coroutines.flow.Flow
import com.spyker3d.easyjob.details.domain.api.GetVacancyDetailsUseCase
import com.spyker3d.easyjob.details.domain.api.VacancyDetailsRepository
import com.spyker3d.easyjob.details.domain.model.VacancyDetails
import com.spyker3d.easyjob.utils.Resource

class GetVacancyDetailsUseCaseImpl(private val vacancyDetailsRepository: VacancyDetailsRepository) :
    GetVacancyDetailsUseCase {
    override suspend fun execute(id: String): Flow<Resource<VacancyDetails>> {
        return vacancyDetailsRepository.getVacancyById(id)
    }
}
