package com.spyker3d.easyjob.filter.domain.impl

import com.spyker3d.easyjob.filter.domain.api.FilterStorageInteractor
import com.spyker3d.easyjob.filter.domain.api.FilterStorageRepository
import com.spyker3d.easyjob.filter.domain.model.AreaFilter
import com.spyker3d.easyjob.filter.domain.model.CountryFilter
import com.spyker3d.easyjob.filter.domain.model.FilterGeneral
import com.spyker3d.easyjob.filter.domain.model.IndustryFilter

class FilterStorageInteractorImpl(
    private val repository: FilterStorageRepository
) : FilterStorageInteractor {
    override fun clearAllFilterParameters() {
        repository.clearAllFilterParameters()
    }

    override fun clearAllSavedParameters() {
        repository.clearAllSavedParameters()
    }

    override fun isFilterActive(): Boolean {
        return repository.isFilterActive()
    }

    override fun saveArea(area: AreaFilter?) {
        repository.saveArea(area)
    }

    override fun saveCountry(country: CountryFilter?) {
        repository.saveCountry(country)
    }

    override fun saveIndustry(industry: IndustryFilter?) {
        repository.saveIndustry(industry)
    }

    override fun saveExpectedSalary(salaryAmount: String) {
        repository.saveExpectedSalary(salaryAmount)
    }

    override fun saveHideNoSalaryItems(hideNoSalaryItems: Boolean) {
        repository.saveHideNoSalaryItems(hideNoSalaryItems)
    }

    override fun getAllFilterParameters(): FilterGeneral {
        return repository.getAllFilterParameters()
    }

    override fun getAllSavedParameters(): FilterGeneral {
        return repository.getAllSavedParameters()
    }

    override fun saveAllFilterParameters(filter: FilterGeneral) {
        repository.saveAllFilterParameters(filter)
    }
}
