package com.spyker3d.easyjob.search.domain.model

data class AreaModel(
    val subAreas: List<AreaModel?>?,
    val id: String?,
    val name: String?,
    val prepositional: String?,
    val parentId: String?,
)
