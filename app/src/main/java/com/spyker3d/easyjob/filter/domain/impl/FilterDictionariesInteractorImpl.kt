package com.spyker3d.easyjob.filter.domain.impl

import kotlinx.coroutines.flow.Flow
import com.spyker3d.easyjob.filter.domain.api.FilterDictionariesInteractor
import com.spyker3d.easyjob.filter.domain.api.FilterDictionariesRepository
import com.spyker3d.easyjob.filter.domain.model.Area
import com.spyker3d.easyjob.filter.domain.model.AreaSuggestion
import com.spyker3d.easyjob.filter.domain.model.Country
import com.spyker3d.easyjob.filter.domain.model.Industry
import com.spyker3d.easyjob.utils.Resource

class FilterDictionariesInteractorImpl(
    private val repository: FilterDictionariesRepository
) : FilterDictionariesInteractor {

    override suspend fun getAreas(): Flow<Resource<List<Area>>> {
        return repository.getAreas()
    }

    override suspend fun getCountries(): Flow<Resource<List<Country>>> {
        return repository.getCountries()
    }

    override suspend fun getSubAreas(areaId: String): Flow<Resource<List<Area>>> {
        return repository.getSubAreas(areaId)
    }

    override suspend fun getIndustries(): Flow<Resource<List<Industry>>> {
        return repository.getIndustries()
    }

    override suspend fun searchInAreas(searchText: String, areaId: String?): Flow<Resource<List<AreaSuggestion>>> {
        return repository.searchInAreas(searchText, areaId)
    }

}
