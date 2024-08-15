package com.spyker3d.easyjob.filter.domain.model

open class Industry(
    val id: String,
    val industries: List<SphereOfIndustry>,
    val name: String,
) : IndustryDetailsFilterItem(industryId = id, industryName = name)
