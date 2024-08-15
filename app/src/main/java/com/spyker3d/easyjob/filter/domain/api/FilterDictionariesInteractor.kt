package com.spyker3d.easyjob.filter.domain.api

import kotlinx.coroutines.flow.Flow
import com.spyker3d.easyjob.filter.domain.model.Area
import com.spyker3d.easyjob.filter.domain.model.AreaSuggestion
import com.spyker3d.easyjob.filter.domain.model.Country
import com.spyker3d.easyjob.filter.domain.model.Industry
import com.spyker3d.easyjob.utils.Resource

interface FilterDictionariesInteractor {
    suspend fun getAreas(): Flow<Resource<List<Area>>>

    suspend fun getCountries(): Flow<Resource<List<Country>>>

    suspend fun getSubAreas(areaId: String): Flow<Resource<List<Area>>>

    suspend fun searchInAreas(
        searchText: String,
        areaId: String? = null
    ): Flow<Resource<List<AreaSuggestion>>>

    suspend fun getIndustries(): Flow<Resource<List<Industry>>>
}
