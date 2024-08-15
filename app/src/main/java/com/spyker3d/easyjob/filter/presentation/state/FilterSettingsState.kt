package com.spyker3d.easyjob.filter.presentation.state

import com.spyker3d.easyjob.filter.domain.model.FilterGeneral

sealed class FilterSettingsState {
    data class Data(
        val filter: FilterGeneral,
        val isActiveApply: Boolean,
        val isActiveReset: Boolean
    ) : FilterSettingsState()
}
