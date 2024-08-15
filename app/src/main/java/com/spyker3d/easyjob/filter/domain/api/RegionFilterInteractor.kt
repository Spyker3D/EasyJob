package com.spyker3d.easyjob.filter.domain.api

import kotlinx.coroutines.flow.Flow
import com.spyker3d.easyjob.filter.domain.model.Area
import com.spyker3d.easyjob.filter.domain.model.AreaFilter
import com.spyker3d.easyjob.filter.domain.model.AreaSuggestion
import com.spyker3d.easyjob.utils.Resource

interface RegionFilterInteractor {
    fun saveRegion(area: AreaFilter)

    fun getAllSavedParameters(): AreaFilter?

    suspend fun getAreas(): Flow<Resource<List<Area>>>

    suspend fun getSubAreas(areaId: String): Flow<Resource<List<Area>>>

    suspend fun searchInAreas(
        searchText: String,
        areaId: String? = null,
    ): Flow<Resource<List<AreaSuggestion>>> // searchText.length = 2..3000
}
