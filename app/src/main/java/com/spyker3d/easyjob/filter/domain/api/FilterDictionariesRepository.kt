package com.spyker3d.easyjob.filter.domain.api

import kotlinx.coroutines.flow.Flow
import com.spyker3d.easyjob.filter.domain.model.Area
import com.spyker3d.easyjob.filter.domain.model.AreaSuggestion
import com.spyker3d.easyjob.filter.domain.model.Country
import com.spyker3d.easyjob.filter.domain.model.Industry
import com.spyker3d.easyjob.utils.Resource

interface FilterDictionariesRepository {
    suspend fun getAreas(): Flow<Resource<List<Area>>>
    suspend fun getDetailedAreas(): Flow<Resource<List<Area>>>
    suspend fun getCountries(): Flow<Resource<List<Country>>>
    suspend fun getCountriesByAreas(): Flow<Resource<List<Area>>>
    suspend fun getDetailedSubAreas(areaId: String): Flow<Resource<List<Area>>>
    suspend fun getSubAreas(areaId: String): Flow<Resource<List<Area>>>
    suspend fun searchInAreas(
        searchText: String,
        areaId: String? = null
    ): Flow<Resource<List<AreaSuggestion>>> // searchText.length = 2..3000

    suspend fun getIndustries(): Flow<Resource<List<Industry>>>
}
