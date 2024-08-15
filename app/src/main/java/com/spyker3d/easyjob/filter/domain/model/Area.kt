package com.spyker3d.easyjob.filter.domain.model

data class Area(
    val subAreas: List<Area>?,
    val id: String,
    val name: String,
    val parentId: String?
) : AreaDetailsFilterItem(areaId = id, areaName = name)
