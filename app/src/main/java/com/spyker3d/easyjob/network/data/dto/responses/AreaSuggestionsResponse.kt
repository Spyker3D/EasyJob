package com.spyker3d.easyjob.network.data.dto.responses

import com.google.gson.annotations.SerializedName
import com.spyker3d.easyjob.network.data.dto.linked.AreaSuggestionDTO

class AreaSuggestionsResponse(
    @SerializedName("items") val suggestions: List<AreaSuggestionDTO>
) : Response()
