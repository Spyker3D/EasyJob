package com.spyker3d.easyjob.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.spyker3d.easyjob.filter.domain.api.PlaceToWorkFilterInteractor
import com.spyker3d.easyjob.filter.domain.model.AreaFilter
import com.spyker3d.easyjob.filter.domain.model.CountryFilter
import com.spyker3d.easyjob.filter.presentation.state.PlaceToWorkFilterState

const val TIMES_TO_TRY = 3
const val DELAY_FOR_PARENT_ID_TRIES = 300L

class PlaceToWorkFilterViewModel(private val placeToWorkFilterInteractor: PlaceToWorkFilterInteractor) : ViewModel() {
    private val _stateLiveData = MutableLiveData<PlaceToWorkFilterState>()
    val stateLiveData: LiveData<PlaceToWorkFilterState> = _stateLiveData

    fun saveFilterAreaParameters() {
        val currentState = _stateLiveData.value as PlaceToWorkFilterState.AreaFilter
        placeToWorkFilterInteractor.saveCountry(
            countryId = currentState.countryId,
            countryName = currentState.countryName
        )
        placeToWorkFilterInteractor.saveArea(
            areaId = currentState.areaId,
            areaName = currentState.areaName
        )
    }

    fun clearCountry() {
        placeToWorkFilterInteractor.clearCountry()
        updateCurrentFilterAreaParameters()
    }

    fun clearArea() {
        placeToWorkFilterInteractor.clearArea()
        updateCurrentFilterAreaParameters()
    }

    fun getCurrentFilterAreaParameters() {
        viewModelScope.launch {
            val currentCountry = placeToWorkFilterInteractor.getCurrentCountryChoice()
            val currentArea = placeToWorkFilterInteractor.getCurrentAreaChoice()
            if (!currentArea?.areaName.isNullOrEmpty() && currentCountry?.countryName.isNullOrEmpty()) {
                getCountryAsParent(currentArea!!)
            } else {
                setLiveDataValue(currentCountry, currentArea)
            }
        }
    }

    private suspend fun getCountryAsParent(area: AreaFilter) {
        var triesLeft: Int = TIMES_TO_TRY
        do {
            val parentOfRegionById =
                placeToWorkFilterInteractor.getCountryForRegion(area.areaId!!)
            if (parentOfRegionById?.countryId != null) {
                setLiveDataValue(parentOfRegionById, area)
                placeToWorkFilterInteractor.saveCountry(
                    countryId = parentOfRegionById.countryId,
                    countryName = parentOfRegionById.countryName
                )
            }
            if (triesLeft < TIMES_TO_TRY) delay(DELAY_FOR_PARENT_ID_TRIES)
            triesLeft--
        } while (parentOfRegionById?.countryId == null && triesLeft >= 0)
    }

    fun updateCurrentFilterAreaParameters() {
        val currentCountry = placeToWorkFilterInteractor.getCurrentCountryChoice()
        val currentArea = placeToWorkFilterInteractor.getCurrentAreaChoice()
        setLiveDataValue(currentCountry, currentArea)
    }

    private fun setLiveDataValue(currentCountry: CountryFilter?, currentArea: AreaFilter?) {
        _stateLiveData.value = PlaceToWorkFilterState.AreaFilter(
            countryId = currentCountry?.countryId,
            countryName = currentCountry?.countryName,
            areaId = currentArea?.areaId,
            areaName = currentArea?.areaName
        )
    }
}
