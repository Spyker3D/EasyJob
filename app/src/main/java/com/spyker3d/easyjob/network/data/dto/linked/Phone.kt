package com.spyker3d.easyjob.network.data.dto.linked

import com.google.gson.annotations.SerializedName

class Phone(
    @SerializedName("city") val city: String,
    @SerializedName("comment") val comment: String?,
    @SerializedName("country") val country: String,
    @SerializedName("formatted") val formatted: String,
    @SerializedName("number") val number: String,
)
