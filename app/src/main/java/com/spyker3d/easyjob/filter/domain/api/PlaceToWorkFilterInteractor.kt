package com.spyker3d.easyjob.filter.domain.api

import com.spyker3d.easyjob.filter.domain.model.AreaFilter
import com.spyker3d.easyjob.filter.domain.model.CountryFilter

interface PlaceToWorkFilterInteractor {
    fun saveCountry(countryId: String?, countryName: String?)

    fun saveArea(areaId: String?, areaName: String?)

    fun clearCountry()

    fun clearArea()

    fun getCurrentCountryChoice(): CountryFilter?

    fun getCurrentAreaChoice(): AreaFilter?

    suspend fun getCountryForRegion(areaId: String): CountryFilter?
}
