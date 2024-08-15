package com.spyker3d.easyjob.search.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.spyker3d.easyjob.search.data.mapper.SearchVacancyConverter
import com.spyker3d.easyjob.search.domain.api.SearchInteractor
import com.spyker3d.easyjob.search.domain.api.SearchRepository
import com.spyker3d.easyjob.search.domain.model.SearchParameters
import com.spyker3d.easyjob.search.domain.model.VacancyListResult
import com.spyker3d.easyjob.utils.Resource

class SearchInteractorImpl(val repository: SearchRepository, val converter: SearchVacancyConverter) : SearchInteractor {
    override fun searchVacancy(
        expression: String,
        parameters: SearchParameters?,
        perPage: Int,
        currentPage: Int
    ): Flow<VacancyListResult> = flow {
        val salaryOnly = parameters?.withSalaryOnly ?: false
        repository
            .searchVacancy(
                textForSearch = expression,
                areaId = parameters?.areaId,
                industryIds = parameters?.industryIds,
                salary = parameters?.salary,
                withSalaryOnly = salaryOnly,
                perPage = perPage,
                page = currentPage
            )
            .collect { vacancyListResponse ->
                when (vacancyListResponse) {
                    is Resource.Success -> {
                        val vacancyList =
                            converter.mapToListVacancyModel(vacancyListResponse.data!!.vacancyAtSearchList)
                        val foundVacancy = vacancyListResponse.data.totalFound
                        val pagesTotal = vacancyListResponse.data.totalPages
                        emit(
                            VacancyListResult(
                                result = vacancyList,
                                errorMessage = "",
                                foundVacancy = foundVacancy.toInt(),
                                page = currentPage,
                                pages = pagesTotal.toInt(),
                            )
                        )
                    }

                    is Resource.Error, is Resource.InternetConnectionError, is Resource.NotFoundError -> {
                        emit(
                            VacancyListResult(emptyList(), vacancyListResponse.message, 0, 0, 0)
                        )
                    }
                }
            }
    }
}
