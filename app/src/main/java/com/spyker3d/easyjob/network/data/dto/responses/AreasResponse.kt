package com.spyker3d.easyjob.network.data.dto.responses

import com.google.gson.annotations.SerializedName
import com.spyker3d.easyjob.network.data.dto.linked.AreaDTO

class AreasResponse(
    @SerializedName("areas") val areasList: List<AreaDTO>
) : Response()
