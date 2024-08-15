package com.spyker3d.easyjob.network.data.dto.responses

import com.google.gson.annotations.SerializedName
import com.spyker3d.easyjob.network.data.dto.linked.VacancyFunctTitle

class VacancySuggestionsResponse(
    @SerializedName("items") val vacancyPositionsList: List<VacancyFunctTitle>
) : Response()
