package com.spyker3d.easyjob.search.domain.model

data class SalaryModel(
    val currency: String,
    val from: Int?,
    val gross: Boolean,
    val to: Int?
)
