package com.spyker3d.easyjob.network.data.dto.responses

import com.google.gson.annotations.SerializedName
import com.spyker3d.easyjob.network.data.dto.linked.Currency

class DictionariesResponse(
    @SerializedName("currency") val currency: List<Currency>
) : Response()
