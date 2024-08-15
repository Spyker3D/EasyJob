package com.spyker3d.easyjob.filter.domain.api

import kotlinx.coroutines.flow.Flow
import com.spyker3d.easyjob.filter.domain.model.Area
import com.spyker3d.easyjob.filter.domain.model.CountryFilter
import com.spyker3d.easyjob.utils.Resource

interface CountryFilterInteractor {
    suspend fun saveCountry(country: CountryFilter)

    fun getAllSavedParameters(): CountryFilter?

    suspend fun getCountries(): Flow<Resource<List<Area>>>

}
