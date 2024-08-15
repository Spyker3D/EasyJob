package com.spyker3d.easyjob.search.domain.api

import kotlinx.coroutines.flow.Flow
import com.spyker3d.easyjob.search.domain.model.SearchParameters
import com.spyker3d.easyjob.search.domain.model.VacancyListResult

interface SearchInteractor {
    fun searchVacancy(
        expression: String,
        parameters: SearchParameters?,
        perPage: Int,
        currentPage: Int
    ): Flow<VacancyListResult>
}
