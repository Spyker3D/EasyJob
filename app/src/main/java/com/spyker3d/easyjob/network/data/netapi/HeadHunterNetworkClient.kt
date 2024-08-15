package com.spyker3d.easyjob.network.data.netapi

import com.spyker3d.easyjob.network.data.dto.HeadHunterRequest
import com.spyker3d.easyjob.network.data.dto.responses.Response

interface HeadHunterNetworkClient {
    suspend fun doRequest(request: HeadHunterRequest): Response
}
