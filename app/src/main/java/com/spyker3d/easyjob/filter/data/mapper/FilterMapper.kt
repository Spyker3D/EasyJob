package com.spyker3d.easyjob.filter.data.mapper

import com.spyker3d.easyjob.filter.domain.model.Area
import com.spyker3d.easyjob.filter.domain.model.AreaSuggestion
import com.spyker3d.easyjob.filter.domain.model.Country
import com.spyker3d.easyjob.filter.domain.model.Industry
import com.spyker3d.easyjob.filter.domain.model.SphereOfIndustry
import com.spyker3d.easyjob.network.data.dto.linked.AreaDTO
import com.spyker3d.easyjob.network.data.dto.linked.AreaSuggestionDTO
import com.spyker3d.easyjob.network.data.dto.linked.CountryDTO
import com.spyker3d.easyjob.network.data.dto.linked.IndustryDTO
import com.spyker3d.easyjob.network.data.dto.linked.IndustrySphereDTO

object FilterMapper {
    fun toArea(areaDTO: AreaDTO): Area =
        Area(
            subAreas = areaDTO.subAreaDTOS.map { toArea(it) },
            id = areaDTO.id,
            name = areaDTO.name,
            parentId = areaDTO.parentId,
        )

    fun toCountry(countryDTO: CountryDTO): Country =
        Country(
            id = countryDTO.id,
            name = countryDTO.name,
            url = countryDTO.url,
        )

    fun toIndustry(industryDTO: IndustryDTO): Industry =
        Industry(
            id = industryDTO.id,
            industries = industryDTO.spheres.map { toSphereOfIndustry(industrySphereDTO = it) },
            name = industryDTO.name,
        )

    private fun toSphereOfIndustry(industrySphereDTO: IndustrySphereDTO): SphereOfIndustry =
        SphereOfIndustry(
            id = industrySphereDTO.id,
            name = industrySphereDTO.sphereName,
        )

    fun toAreaSuggestion(areaSuggestionDTO: AreaSuggestionDTO): AreaSuggestion =
        AreaSuggestion(
            id = areaSuggestionDTO.id,
            name = areaSuggestionDTO.name,
            url = areaSuggestionDTO.url,
        )
}
