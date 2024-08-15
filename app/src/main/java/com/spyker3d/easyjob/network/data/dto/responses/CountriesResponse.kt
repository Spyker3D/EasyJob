package com.spyker3d.easyjob.network.data.dto.responses

import com.spyker3d.easyjob.network.data.dto.linked.CountryDTO

class CountriesResponse(
    val countriesList: List<CountryDTO>
) : Response()
