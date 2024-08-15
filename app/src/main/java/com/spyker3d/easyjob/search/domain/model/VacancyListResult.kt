package com.spyker3d.easyjob.search.domain.model

data class VacancyListResult(
    val result: List<Vacancy>?,
    val errorMessage: String? = null,
    val foundVacancy: Int,
    val page: Int,
    val pages: Int
)
