package com.spyker3d.easyjob.filter.presentation.model

import com.spyker3d.easyjob.filter.domain.model.Industry

data class IndustryWithCheck(
    val industry: Industry,
    val isChecked: Boolean = false
)
