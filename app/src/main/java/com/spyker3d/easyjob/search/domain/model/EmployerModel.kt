package com.spyker3d.easyjob.search.domain.model

data class EmployerModel(
    val id: String?,
    val name: String,
    val url: String?,
    val vacanciesUrl: String?,
    val isTrusted: Boolean,
    val logoUrls: LogoUrlsModel?,
)
