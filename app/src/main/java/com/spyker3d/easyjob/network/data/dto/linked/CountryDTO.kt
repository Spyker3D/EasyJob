package com.spyker3d.easyjob.network.data.dto.linked

import com.google.gson.annotations.SerializedName

class CountryDTO(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
)
