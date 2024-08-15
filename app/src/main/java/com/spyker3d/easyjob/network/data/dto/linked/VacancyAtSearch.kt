package com.spyker3d.easyjob.network.data.dto.linked

import com.google.gson.annotations.SerializedName

class VacancyAtSearch(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("salary") val salary: Salary?,
    @SerializedName("employer") val employer: Employer?,
    @SerializedName("brand_snippet") val brandSnippet: BrandSnippet?,
    @SerializedName("area") val areaDTO: AreaDTO?,
)
