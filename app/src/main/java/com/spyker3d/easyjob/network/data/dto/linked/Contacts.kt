package com.spyker3d.easyjob.network.data.dto.linked

import com.google.gson.annotations.SerializedName

class Contacts(
    @SerializedName("email") val email: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("phones") val phones: List<Phone>?,
)
