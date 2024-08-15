package com.spyker3d.easyjob.filter.domain.model

class AreaSuggestion(
    val id: String,
    val name: String,
    val url: String,
) : AreaDetailsFilterItem(areaId = id, areaName = name)
