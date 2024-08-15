package com.spyker3d.easyjob.search.domain.model

data class Vacancy(
    val id: String,
    val name: String,
    val salary: SalaryModel?,
    val employer: EmployerModel?,
    val brandSnippet: BrandSnippetModel?,
    val area: AreaModel?,
)
