package com.spyker3d.easyjob.search.domain.api

import kotlinx.coroutines.flow.Flow
import com.spyker3d.easyjob.network.data.dto.linked.AreaDTO
import com.spyker3d.easyjob.network.data.dto.linked.CountryDTO
import com.spyker3d.easyjob.network.data.dto.linked.IndustryDTO
import com.spyker3d.easyjob.network.data.dto.linked.Locale
import com.spyker3d.easyjob.network.data.dto.linked.VacancyFunctTitle
import com.spyker3d.easyjob.network.data.dto.responses.DictionariesResponse
import com.spyker3d.easyjob.network.data.dto.responses.VacancyListResponse
import com.spyker3d.easyjob.utils.Resource

interface SearchRepository {
    suspend fun getLocales(): Flow<Resource<List<Locale>>>
    suspend fun getDictionaries(): Flow<Resource<DictionariesResponse>>
    suspend fun getIndustries(): Flow<Resource<List<IndustryDTO>>>
    suspend fun getAreas(): Flow<Resource<List<AreaDTO>>>
    suspend fun getCountries(): Flow<Resource<List<CountryDTO>>>
    suspend fun getVacancySuggestions(textForSuggestions: String): Flow<Resource<List<VacancyFunctTitle>>>
    suspend fun searchVacancy(
        textForSearch: String,
        areaId: String? = null,
        industryIds: List<String>? = null,
        currencyCode: String? = null,
        salary: Int? = null,
        withSalaryOnly: Boolean = false,
        page: Int? = null,
        perPage: Int? = null,
    ): Flow<Resource<VacancyListResponse>>
}
