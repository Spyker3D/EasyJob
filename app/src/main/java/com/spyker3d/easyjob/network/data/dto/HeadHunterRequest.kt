package com.spyker3d.easyjob.network.data.dto

sealed class HeadHunterRequest {
    data object Locales : HeadHunterRequest()
    data object Dictionaries : HeadHunterRequest()
    data object Industries : HeadHunterRequest()
    data object Areas : HeadHunterRequest()
    data object Counties : HeadHunterRequest()
    data class VacancySuggestions(val textForSuggestions: String) : HeadHunterRequest()
    data class VacancySearch(
        val textForSearch: String,
        val areaId: String? = null,
        val industryIds: List<String>? = null,
        val currencyCode: String? = null,
        val salary: Int? = null,
        val withSalaryOnly: Boolean = false,
        val page: Int?,
        val perPage: Int?,
    ) : HeadHunterRequest()

    data class VacancyById(val id: String) : HeadHunterRequest()
    data class SubAreas(val areaId: String) : HeadHunterRequest()
    data class SearchInAreas(val searchText: String, val areaId: String?) : HeadHunterRequest()
}
