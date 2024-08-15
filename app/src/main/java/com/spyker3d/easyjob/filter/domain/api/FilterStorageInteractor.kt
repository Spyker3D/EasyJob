package com.spyker3d.easyjob.filter.domain.api

import com.spyker3d.easyjob.filter.domain.model.AreaFilter
import com.spyker3d.easyjob.filter.domain.model.CountryFilter
import com.spyker3d.easyjob.filter.domain.model.FilterGeneral
import com.spyker3d.easyjob.filter.domain.model.IndustryFilter

interface FilterStorageInteractor {
    fun saveAllFilterParameters(filter: FilterGeneral)

    fun getAllFilterParameters(): FilterGeneral

    fun getAllSavedParameters(): FilterGeneral

    fun clearAllFilterParameters()

    fun isFilterActive(): Boolean

    fun saveArea(area: AreaFilter?)

    fun saveCountry(country: CountryFilter?)

    fun saveIndustry(industry: IndustryFilter?)

    fun saveExpectedSalary(salaryAmount: String)

    fun saveHideNoSalaryItems(hideNoSalaryItems: Boolean)
    fun clearAllSavedParameters()
}
