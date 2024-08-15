package com.spyker3d.easyjob.search.domain.model

class SearchParameters(
    val areaId: String?,
    val industryIds: List<String>?,
    val currencyCode: String?,
    val salary: Int?,
    val withSalaryOnly: Boolean = false,
    val page: Int?,
    val perPage: Int?,
)

