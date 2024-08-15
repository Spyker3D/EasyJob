package com.spyker3d.easyjob.filter.data.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.spyker3d.easyjob.R
import com.spyker3d.easyjob.filter.data.mapper.FilterMapper
import com.spyker3d.easyjob.filter.domain.api.FilterDictionariesRepository
import com.spyker3d.easyjob.filter.domain.model.Area
import com.spyker3d.easyjob.filter.domain.model.AreaSuggestion
import com.spyker3d.easyjob.filter.domain.model.Country
import com.spyker3d.easyjob.filter.domain.model.Industry
import com.spyker3d.easyjob.network.data.dto.HeadHunterRequest
import com.spyker3d.easyjob.network.data.dto.responses.AreaSuggestionsResponse
import com.spyker3d.easyjob.network.data.dto.responses.AreasResponse
import com.spyker3d.easyjob.network.data.dto.responses.CountriesResponse
import com.spyker3d.easyjob.network.data.dto.responses.IndustryResponse
import com.spyker3d.easyjob.network.data.dto.responses.Response
import com.spyker3d.easyjob.network.data.netapi.HeadHunterNetworkClient
import com.spyker3d.easyjob.network.data.netapi.MAX_AREA_SUGGESTION_REQUEST_TEXT_LENGTH
import com.spyker3d.easyjob.network.data.netapi.MIN_AREA_SUGGESTION_REQUEST_TEXT_LENGTH
import com.spyker3d.easyjob.utils.Resource
import java.text.Collator

private const val OTHER_REGIONS = "Другие регионы"

class FilterDictionariesRepositoryHHNetworkClientBased(private val client: HeadHunterNetworkClient, context: Context) :
    FilterDictionariesRepository {
    private val industriesErrorMessage = context.getString(R.string.net_industry_dictionary_income_error_message)
    private val areasErrorMessage = context.getString(R.string.net_areas_dictionary_income_error_message)
    private val countriesErrorMessage = context.getString(R.string.net_countries_dictionary_income_error_message)
    private val areasSuggestionsErrorMessage =
        context.getString(R.string.net_area_suggestions_income_error_message)
    private val areaSuggestionsRequestLengthErrorMessage =
        context.getString(R.string.net_area_suggestions_request_text_length_error_message)

    override suspend fun getIndustries(): Flow<Resource<List<Industry>>> = flow {
        val response = client.doRequest(HeadHunterRequest.Industries)
        when (response.resultCode) {
            Response.SUCCESS -> {
                emit(
                    Resource.Success((response as IndustryResponse).industriesList.map { industryDTO ->
                        FilterMapper.toIndustry(industryDTO)
                    })
                )
            }

            Response.NOT_FOUND -> emit(Resource.NotFoundError(industriesErrorMessage))
            Response.NO_INTERNET -> emit(Resource.InternetConnectionError(industriesErrorMessage))
            else -> emit(Resource.Error(industriesErrorMessage))
        }
    }

    override suspend fun getAreas(): Flow<Resource<List<Area>>> = flow {
        val response = client.doRequest(HeadHunterRequest.Areas)
        when (response.resultCode) {
            Response.SUCCESS -> {
                emit(
                    Resource.Success((response as AreasResponse).areasList.map { areaDto ->
                        FilterMapper.toArea(areaDto)
                    })
                )
            }

            Response.NOT_FOUND -> emit(Resource.NotFoundError(areasErrorMessage))
            Response.NO_INTERNET -> emit(Resource.InternetConnectionError(areasErrorMessage))
            else -> emit(Resource.Error(areasErrorMessage))
        }
    }

    override suspend fun getDetailedAreas(): Flow<Resource<List<Area>>> = flow {
        val response = client.doRequest(HeadHunterRequest.Areas)
        when (response.resultCode) {
            Response.SUCCESS -> {
                val subAreasListOriginal = (response as AreasResponse).areasList.map { areaDto ->
                    FilterMapper.toArea(areaDto)
                }
                val detailedAreaList = getMaximumDetailedArea(subAreasListOriginal)
                val regionList = getRegionList(subAreasListOriginal)
                val finalRegionList = regionList + detailedAreaList
                val collator = Collator.getInstance()
                val sortedDetailedList = finalRegionList.sortedWith(compareBy(collator) { it.name })
                emit(Resource.Success(sortedDetailedList))
            }
            Response.NOT_FOUND -> emit(Resource.NotFoundError(areasErrorMessage))
            Response.NO_INTERNET -> emit(Resource.InternetConnectionError(areasErrorMessage))
            else -> emit(Resource.Error(areasErrorMessage))
        }
    }

    override suspend fun getCountries(): Flow<Resource<List<Country>>> = flow {
        val response = client.doRequest(HeadHunterRequest.Counties)
        when (response.resultCode) {
            Response.SUCCESS -> {
                emit(
                    Resource.Success((response as CountriesResponse).countriesList.map { countryDto ->
                        FilterMapper.toCountry(countryDto)
                    })
                )
            }

            Response.NOT_FOUND -> emit(Resource.NotFoundError(countriesErrorMessage))
            Response.NO_INTERNET -> emit(Resource.InternetConnectionError(countriesErrorMessage))
            else -> emit(Resource.Error(countriesErrorMessage))
        }
    }

    override suspend fun getCountriesByAreas(): Flow<Resource<List<Area>>> = flow {
        getAreas().collect { result ->
            when (result) {
                is Resource.Success -> {
                    val areasWithParentId = result.data!!.filter { area ->
                        area.parentId == null
                    }
                    val otherRegionsItem = areasWithParentId.find { it.name == OTHER_REGIONS }
                    if (otherRegionsItem != null) {
                        val filteredCountriesList = areasWithParentId - otherRegionsItem
                        val updatedCountriesList = filteredCountriesList + otherRegionsItem
                        emit(Resource.Success(updatedCountriesList))
                    } else {
                        emit(Resource.Success(areasWithParentId))
                    }
                }

                is Resource.Error -> emit(Resource.Error(result.message!!))

                is Resource.InternetConnectionError -> emit(Resource.InternetConnectionError(result.message!!))

                is Resource.NotFoundError -> emit(Resource.NotFoundError(result.message!!))
            }
        }
    }

    override suspend fun getDetailedSubAreas(areaId: String): Flow<Resource<List<Area>>> {
        return flow {
            val response = client.doRequest(HeadHunterRequest.SubAreas(areaId))
            if (response.resultCode == Response.SUCCESS) {
                val subAreasListOriginal = (response as AreasResponse).areasList.map { areaDto ->
                    FilterMapper.toArea(areaDto)
                }
                val detailedAreaList = getMaximumDetailedArea(subAreasListOriginal)
                val regionList = getRegionList(subAreasListOriginal)
                val finalRegionList = regionList + detailedAreaList
                val collator = Collator.getInstance()
                val sortedDetailedList = finalRegionList.sortedWith(compareBy(collator) { it.name })
                emit(Resource.Success(sortedDetailedList))
            } else {
                emit(Resource.Error(areasErrorMessage))
            }
        }
    }

    private fun getRegionList(originalAreaList: List<Area>): List<Area> {
        val regionList = mutableListOf<Area>()
        originalAreaList.forEach { area ->
            if (area.parentId != null && !area.subAreas.isNullOrEmpty()) {
                regionList.add(area)
            } else {
                regionList.addAll(getRegionList(area.subAreas!!))
            }
        }
        return regionList
    }

    private fun getMaximumDetailedArea(originalList: List<Area>): List<Area> {
        val detailedAreaList = mutableListOf<Area>()
        originalList.forEach { area ->
            if (area.subAreas.isNullOrEmpty()) {
                detailedAreaList.add(area)
            } else {
                detailedAreaList.addAll(getMaximumDetailedArea(area.subAreas))
            }
        }
        return detailedAreaList
    }

    override suspend fun getSubAreas(areaId: String): Flow<Resource<List<Area>>> {
        return flow {
            val response = client.doRequest(HeadHunterRequest.SubAreas(areaId))
            when (response.resultCode) {
                Response.SUCCESS -> {
                    emit(
                        Resource.Success((response as AreasResponse).areasList.map { areaDto ->
                            FilterMapper.toArea(areaDto)
                        })
                    )
                }

                Response.NOT_FOUND -> emit(Resource.NotFoundError(areasErrorMessage))
                Response.NO_INTERNET -> emit(Resource.InternetConnectionError(areasErrorMessage))
                else -> emit(Resource.Error(areasErrorMessage))
            }
        }
    }

    override suspend fun searchInAreas(searchText: String, areaId: String?): Flow<Resource<List<AreaSuggestion>>> {
        return flow {
            if (searchText.length in MIN_AREA_SUGGESTION_REQUEST_TEXT_LENGTH..MAX_AREA_SUGGESTION_REQUEST_TEXT_LENGTH) {
                val response = client.doRequest(
                    HeadHunterRequest.SearchInAreas(searchText, areaId)
                )
                when (response.resultCode) {
                    Response.SUCCESS -> {
                        emit(
                            Resource.Success((response as AreaSuggestionsResponse)
                                .suggestions.map { areaSuggestionDTO ->
                                    FilterMapper.toAreaSuggestion(areaSuggestionDTO)
                                })
                        )
                    }

                    Response.NOT_FOUND -> emit(Resource.NotFoundError(areasSuggestionsErrorMessage))
                    Response.NO_INTERNET -> emit(Resource.InternetConnectionError(areasSuggestionsErrorMessage))
                    else -> emit(Resource.Error(areasSuggestionsErrorMessage))
                }
            } else {
                emit(Resource.Error(areaSuggestionsRequestLengthErrorMessage))
            }
        }
    }
}
