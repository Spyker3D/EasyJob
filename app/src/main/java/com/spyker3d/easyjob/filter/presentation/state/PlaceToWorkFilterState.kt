package com.spyker3d.easyjob.filter.presentation.state

sealed class PlaceToWorkFilterState {
    class AreaFilter(
        val countryId: String? = null,
        val countryName: String? = null,
        val areaId: String? = null,
        val areaName: String? = null,
        val isRegionInCountry: Boolean = true,
    ) : PlaceToWorkFilterState()
}
