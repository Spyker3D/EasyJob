package com.spyker3d.easyjob.filter.presentation.state

import com.spyker3d.easyjob.filter.domain.model.AreaDetailsFilterItem

sealed class AreaFilterState {
    class AreaContent(val listOfAreas: List<AreaDetailsFilterItem>) : AreaFilterState()
    class Error(val message: String) : AreaFilterState()
    class InternetConnectionError(val message: String) : AreaFilterState()
    data object Loading : AreaFilterState()
    data object Empty : AreaFilterState()
}
