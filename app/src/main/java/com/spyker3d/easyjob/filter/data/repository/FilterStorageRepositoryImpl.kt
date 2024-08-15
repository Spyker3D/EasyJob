package com.spyker3d.easyjob.filter.data.repository

import com.spyker3d.easyjob.filter.data.storage.FilterStorage
import com.spyker3d.easyjob.filter.domain.api.FilterStorageRepository
import com.spyker3d.easyjob.filter.domain.model.AreaFilter
import com.spyker3d.easyjob.filter.domain.model.CountryFilter
import com.spyker3d.easyjob.filter.domain.model.FilterGeneral
import com.spyker3d.easyjob.filter.domain.model.IndustryFilter

class FilterStorageRepositoryImpl(private val filterStorage: FilterStorage) :
    FilterStorageRepository {
    override fun saveAllFilterParameters(filter: FilterGeneral) {
        filterStorage.saveFinalFilterParameters(filter)
    }

    override fun getAllFilterParameters(): FilterGeneral = filterStorage.getAllFinalFilterParameters()

    override fun getAllSavedParameters(): FilterGeneral = filterStorage.getAllSavedParameter()

    override fun clearAllFilterParameters() {
        filterStorage.clearAllFilterParameters()
    }

    override fun isFilterActive(): Boolean {
        val finalFilterSaved = filterStorage.getAllFinalFilterParameters()
        return (finalFilterSaved.country != null || finalFilterSaved.area != null
            || finalFilterSaved.industry != null || finalFilterSaved.expectedSalary != null
            || finalFilterSaved.hideNoSalaryItems != null
            && finalFilterSaved.hideNoSalaryItems != false)
    }

    override fun saveArea(area: AreaFilter?) {
        val specificFilterSaved = filterStorage.getAllSavedParameter()
        filterStorage.saveSpecificFilterParameters(specificFilterSaved.copy(area = area))
    }

    override fun saveCountry(country: CountryFilter?) {
        val specificFilterSaved = filterStorage.getAllSavedParameter()
        filterStorage.saveSpecificFilterParameters(specificFilterSaved.copy(country = country))
    }

    override fun saveIndustry(industry: IndustryFilter?) {
        val specificFilterSaved = filterStorage.getAllSavedParameter()
        filterStorage.saveSpecificFilterParameters(specificFilterSaved.copy(industry = industry))
    }

    override fun saveExpectedSalary(salaryAmount: String) {
        val specificFilterSaved = filterStorage.getAllSavedParameter()
        filterStorage.saveSpecificFilterParameters(specificFilterSaved.copy(expectedSalary = salaryAmount))
    }

    override fun saveHideNoSalaryItems(hideNoSalaryItems: Boolean) {
        val specificFilterSaved = filterStorage.getAllSavedParameter()
        filterStorage.saveSpecificFilterParameters(specificFilterSaved.copy(hideNoSalaryItems = hideNoSalaryItems))

    }

    override fun clearAllSavedParameters() {
        filterStorage.clearAllSavedParameters()
    }
}
