package com.spyker3d.easyjob.network.data.dto.linked

import com.google.gson.annotations.SerializedName

class Currency(
    @SerializedName("abbr") var alias: String,
    @SerializedName("code") var code: String,
    @SerializedName("default") var isDefault: Boolean,
    @SerializedName("in_use") var isInUse: Boolean,
    @SerializedName("name") var name: String,
    @SerializedName("rate") var rate: Number,
)
