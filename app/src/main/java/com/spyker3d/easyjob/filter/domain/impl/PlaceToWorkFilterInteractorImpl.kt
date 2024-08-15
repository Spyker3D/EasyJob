package com.spyker3d.easyjob.filter.domain.impl

import com.spyker3d.easyjob.filter.domain.api.FilterDictionariesRepository
import com.spyker3d.easyjob.filter.domain.api.FilterStorageRepository
import kotlinx.coroutines.flow.first
import com.spyker3d.easyjob.filter.domain.api.PlaceToWorkFilterInteractor
import com.spyker3d.easyjob.filter.domain.model.Area
import com.spyker3d.easyjob.filter.domain.model.AreaFilter
import com.spyker3d.easyjob.filter.domain.model.CountryFilter
import com.spyker3d.easyjob.utils.Resource

class PlaceToWorkFilterInteractorImpl(
    private val filterStorageRepository: FilterStorageRepository,
    private val filterDictionariesRepository: FilterDictionariesRepository,
) :
    PlaceToWorkFilterInteractor {
    override fun saveCountry(countryId: String?, countryName: String?) {
        filterStorageRepository.saveCountry(CountryFilter(countryId = countryId, countryName = countryName))
    }

    override fun saveArea(areaId: String?, areaName: String?) {
        filterStorageRepository.saveArea(AreaFilter(areaId = areaId, areaName = areaName))
    }

    override fun clearCountry() {
        filterStorageRepository.saveCountry(CountryFilter(countryId = null, countryName = null))
    }

    override fun clearArea() {
        filterStorageRepository.saveArea(AreaFilter(areaId = null, areaName = null))
    }

    override fun getCurrentCountryChoice(): CountryFilter? = filterStorageRepository.getAllSavedParameters().country

    override fun getCurrentAreaChoice(): AreaFilter? = filterStorageRepository.getAllSavedParameters().area

    override suspend fun getCountryForRegion(areaId: String): CountryFilter? {
        val result = filterDictionariesRepository.getAreas().first()
        if (result is Resource.Success) {
            val resultList = result.data!!
            val areaById = findAreaById(areaId, resultList) ?: return null
            var currentArea: Area? = areaById
            while (currentArea?.parentId != null) {
                currentArea = findParentArea(currentArea, resultList)
            }
            if (currentArea?.areaId != null) {
                return CountryFilter(countryId = currentArea.id, countryName = currentArea.name)
            }
        }
        return null
    }

    private fun findAreaById(areaId: String, generalListOfAreas: List<Area>?): Area? {
        generalListOfAreas?.forEach { area ->
            findAreaBySubArea(areaId, area)?.let { return it }
        }
        return null
    }

    private fun findAreaBySubArea(areaId: String, area: Area): Area? {
        if (area.id == areaId) {
            return area
        }
        return findAreaById(areaId, area.subAreas)

    }

    private fun findParentArea(area: Area, areaList: List<Area>) =
        areaList.asFlatSequence().find { it.id == area.parentId }

    private fun List<Area>.asFlatSequence(): Sequence<Area> = asSequence()
        .flatMap {
            sequenceOf(it) + it.subAreas?.asFlatSequence().orEmpty()
        }
}
