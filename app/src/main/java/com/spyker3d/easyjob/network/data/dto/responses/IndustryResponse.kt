package com.spyker3d.easyjob.network.data.dto.responses

import com.spyker3d.easyjob.network.data.dto.linked.IndustryDTO

class IndustryResponse(
    val industriesList: List<IndustryDTO>
) : Response()
