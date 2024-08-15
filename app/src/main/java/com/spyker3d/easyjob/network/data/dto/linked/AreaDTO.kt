package com.spyker3d.easyjob.network.data.dto.linked

import com.google.gson.annotations.SerializedName

class AreaDTO(
    @SerializedName("areas") val subAreaDTOS: List<AreaDTO>,
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("name_prepositional") val prepositional: String?, // поддерживается только для русской локализации
    @SerializedName("parent_id") val parentId: String?,
)
