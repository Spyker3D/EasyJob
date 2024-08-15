package com.spyker3d.easyjob.filter.domain.impl

import com.spyker3d.easyjob.filter.domain.api.FilterDictionariesRepository
import com.spyker3d.easyjob.filter.domain.api.FilterStorageRepository
import kotlinx.coroutines.flow.Flow
import com.spyker3d.easyjob.filter.domain.api.RegionFilterInteractor
import com.spyker3d.easyjob.filter.domain.model.Area
import com.spyker3d.easyjob.filter.domain.model.AreaFilter
import com.spyker3d.easyjob.filter.domain.model.AreaSuggestion
import com.spyker3d.easyjob.utils.Resource

class RegionFilterInteractorImpl(
    private val filterStorageRepository: FilterStorageRepository,
    private val filterDictionariesRepository: FilterDictionariesRepository,
) : RegionFilterInteractor {
    override fun saveRegion(area: AreaFilter) {
        filterStorageRepository.saveArea(area)
    }

    override fun getAllSavedParameters(): AreaFilter? = filterStorageRepository.getAllSavedParameters().area

    override suspend fun getAreas(): Flow<Resource<List<Area>>> = filterDictionariesRepository.getDetailedAreas()

    override suspend fun getSubAreas(areaId: String): Flow<Resource<List<Area>>> =
        filterDictionariesRepository.getDetailedSubAreas(areaId)

    override suspend fun searchInAreas(searchText: String, areaId: String?): Flow<Resource<List<AreaSuggestion>>> =
        filterDictionariesRepository.searchInAreas(searchText, areaId)
}
