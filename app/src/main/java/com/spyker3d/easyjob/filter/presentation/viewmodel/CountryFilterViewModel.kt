package com.spyker3d.easyjob.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.spyker3d.easyjob.filter.domain.api.CountryFilterInteractor
import com.spyker3d.easyjob.filter.domain.model.Area
import com.spyker3d.easyjob.filter.domain.model.AreaDetailsFilterItem
import com.spyker3d.easyjob.filter.domain.model.CountryFilter
import com.spyker3d.easyjob.filter.presentation.state.AreaFilterState
import com.spyker3d.easyjob.utils.Resource
import com.spyker3d.easyjob.utils.SingleLiveEvent

class CountryFilterViewModel(private val countryFilterInteractor: CountryFilterInteractor) : ViewModel() {
    private val _stateLiveDataCountry = MutableLiveData<AreaFilterState>(AreaFilterState.Loading)
    val stateLiveDataCountry: LiveData<AreaFilterState> = _stateLiveDataCountry
    val backEvent = SingleLiveEvent<Unit>()

    init {
        viewModelScope.launch {
            countryFilterInteractor.getCountries().collect {
                processSearchCountriesListRequest(it)
            }
        }
    }

    private fun processSearchCountriesListRequest(searchCountriesResult: Resource<List<Area>>) {
        if (searchCountriesResult.data != null) {
            val countryFilterState = AreaFilterState.AreaContent(searchCountriesResult.data)
            _stateLiveDataCountry.value = countryFilterState
        } else {
            _stateLiveDataCountry.value = AreaFilterState.Error(searchCountriesResult.message!!)
        }

        when (searchCountriesResult) {
            is Resource.Success -> {
                if (searchCountriesResult.data!!.isNotEmpty()) {
                    _stateLiveDataCountry.value = AreaFilterState.AreaContent(searchCountriesResult.data)
                } else {
                    _stateLiveDataCountry.value = AreaFilterState.Empty
                }
            }
            is Resource.Error, is Resource.NotFoundError -> {
                _stateLiveDataCountry.value =
                    AreaFilterState.Error(searchCountriesResult.message!!)
            }
            is Resource.InternetConnectionError -> _stateLiveDataCountry.value =
                AreaFilterState.InternetConnectionError(searchCountriesResult.message!!)
        }
    }

    fun saveCountryChoiceToFilter(country: AreaDetailsFilterItem) {
        viewModelScope.launch {
            countryFilterInteractor.saveCountry(
                CountryFilter(countryId = country.areaId, countryName = country.areaName)
            )
            backEvent.value = Unit
        }
    }
}
