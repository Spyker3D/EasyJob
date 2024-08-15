package com.spyker3d.easyjob.network.data.dto.linked

import com.google.gson.annotations.SerializedName

class Salary(
    @SerializedName("currency") val currency: String,
    @SerializedName("from") val from: Int?,
    @SerializedName("gross") val gross: Boolean,
    @SerializedName("to") val to: Int?,
)
