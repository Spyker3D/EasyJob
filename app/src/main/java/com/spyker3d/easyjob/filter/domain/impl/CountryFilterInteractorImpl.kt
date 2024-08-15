package com.spyker3d.easyjob.filter.domain.impl

import kotlinx.coroutines.flow.Flow
import com.spyker3d.easyjob.filter.domain.api.CountryFilterInteractor
import com.spyker3d.easyjob.filter.domain.api.FilterDictionariesRepository
import com.spyker3d.easyjob.filter.domain.api.FilterStorageRepository
import com.spyker3d.easyjob.filter.domain.api.PlaceToWorkFilterInteractor
import com.spyker3d.easyjob.filter.domain.model.Area
import com.spyker3d.easyjob.filter.domain.model.AreaFilter
import com.spyker3d.easyjob.filter.domain.model.CountryFilter
import com.spyker3d.easyjob.utils.Resource

class CountryFilterInteractorImpl(
    private val filterStorageRepository: FilterStorageRepository,
    private val filterDictionariesRepository: FilterDictionariesRepository,
    private val placeToWorkFilterInteractor: PlaceToWorkFilterInteractor
) : CountryFilterInteractor {
    override suspend fun saveCountry(country: CountryFilter) {
        filterStorageRepository.saveCountry(country)
        val areaId = filterStorageRepository.getAllSavedParameters().area?.areaId ?: return

        val countryForRegion = placeToWorkFilterInteractor.getCountryForRegion(areaId)
        if (countryForRegion?.countryId != country.countryId) {
            filterStorageRepository.saveArea(AreaFilter(areaId = null, areaName = null))
        }
    }

    override fun getAllSavedParameters(): CountryFilter? = filterStorageRepository.getAllSavedParameters().country

    override suspend fun getCountries(): Flow<Resource<List<Area>>> = filterDictionariesRepository.getCountriesByAreas()
}
