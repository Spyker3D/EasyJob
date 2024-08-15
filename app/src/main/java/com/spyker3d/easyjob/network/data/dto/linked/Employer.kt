package com.spyker3d.easyjob.network.data.dto.linked

import com.google.gson.annotations.SerializedName

class Employer(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String?,
    @SerializedName("vacancies_url") val vacanciesUrl: String?,
    @SerializedName("trusted") val isTrusted: Boolean,
    @SerializedName("logo_urls") val logoUrls: LogoUrls?,
)
