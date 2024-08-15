package com.spyker3d.easyjob.network.data.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.spyker3d.easyjob.R
import com.spyker3d.easyjob.details.domain.api.VacancyDetailsRepository
import com.spyker3d.easyjob.details.domain.model.VacancyDetails
import com.spyker3d.easyjob.network.data.dto.HeadHunterRequest
import com.spyker3d.easyjob.network.data.dto.linked.AreaDTO
import com.spyker3d.easyjob.network.data.dto.linked.CountryDTO
import com.spyker3d.easyjob.network.data.dto.linked.IndustryDTO
import com.spyker3d.easyjob.network.data.dto.linked.Locale
import com.spyker3d.easyjob.network.data.dto.linked.VacancyFunctTitle
import com.spyker3d.easyjob.network.data.dto.responses.AreasResponse
import com.spyker3d.easyjob.network.data.dto.responses.CountriesResponse
import com.spyker3d.easyjob.network.data.dto.responses.DictionariesResponse
import com.spyker3d.easyjob.network.data.dto.responses.IndustryResponse
import com.spyker3d.easyjob.network.data.dto.responses.LocalesResponse
import com.spyker3d.easyjob.network.data.dto.responses.Response
import com.spyker3d.easyjob.network.data.dto.responses.VacancyByIdResponse
import com.spyker3d.easyjob.network.data.dto.responses.VacancyListResponse
import com.spyker3d.easyjob.network.data.dto.responses.VacancySuggestionsResponse
import com.spyker3d.easyjob.network.data.mapper.VacancyDetailsMapper.mapToDomain
import com.spyker3d.easyjob.network.data.netapi.HeadHunterNetworkClient
import com.spyker3d.easyjob.network.data.netapi.MAX_VACANCY_SUGGESTION_REQUEST_TEXT_LENGTH
import com.spyker3d.easyjob.network.data.netapi.MIN_VACANCY_SUGGESTION_REQUEST_TEXT_LENGTH
import com.spyker3d.easyjob.search.domain.api.SearchRepository
import com.spyker3d.easyjob.utils.Resource

class HeadHunterRepository(private val client: HeadHunterNetworkClient, context: Context) : SearchRepository,
    VacancyDetailsRepository {
    private val commonDictionaryErrorMessage = context.getString(R.string.net_common_dictionary_income_error_message)
    private val localeDictionaryErrorMessage = context.getString(R.string.net_locales_dictionary_income_error_message)
    private val industriesErrorMessage = context.getString(R.string.net_industry_dictionary_income_error_message)
    private val areasErrorMessage = context.getString(R.string.net_areas_dictionary_income_error_message)
    private val countriesErrorMessage = context.getString(R.string.net_countries_dictionary_income_error_message)
    private val vacancySuggestionsErrorMessage =
        context.getString(R.string.net_vacancy_suggestions_income_error_message)
    private val vacancySuggestionsRequestLengthErrorMessage =
        context.getString(R.string.net_vacancy_suggestions_request_text_length_error_message)
    private val vacancySearchErrorMessage = context.getString(R.string.net_vacancy_search_income_error_message)
    private val vacancyGetByIdErrorMessage = context.getString(R.string.net_vacancy_get_by_id_income_error_message)
    private val timeoutErrorMessage = context.getString(R.string.net_timeout_error_message)

    override suspend fun getLocales(): Flow<Resource<List<Locale>>> = flow {
        val response = client.doRequest(HeadHunterRequest.Locales)
        if (response.resultCode == Response.SUCCESS) {
            emit(Resource.Success((response as LocalesResponse).localeList))
        } else {
            emit(Resource.Error(localeDictionaryErrorMessage))
        }
    }

    override suspend fun getDictionaries(): Flow<Resource<DictionariesResponse>> =
        flow {
            val response =
                client.doRequest(HeadHunterRequest.Dictionaries)
            if (response.resultCode == Response.SUCCESS) {
                emit(Resource.Success(response as DictionariesResponse))
            } else {
                emit(Resource.Error(commonDictionaryErrorMessage))
            }
        }

    override suspend fun getIndustries(): Flow<Resource<List<IndustryDTO>>> =
        flow {
            val response = client.doRequest(HeadHunterRequest.Industries)
            if (response.resultCode == Response.SUCCESS) {
                emit(Resource.Success((response as IndustryResponse).industriesList))
            } else {
                emit(Resource.Error(industriesErrorMessage))
            }
        }

    override suspend fun getAreas(): Flow<Resource<List<AreaDTO>>> =
        flow {
            val response = client.doRequest(HeadHunterRequest.Areas)
            if (response.resultCode == Response.SUCCESS) {
                emit(Resource.Success((response as AreasResponse).areasList))
            } else {
                emit(Resource.Error(areasErrorMessage))
            }
        }

    override suspend fun getCountries(): Flow<Resource<List<CountryDTO>>> =
        flow {
            val response = client.doRequest(HeadHunterRequest.Counties)
            if (response.resultCode == Response.SUCCESS) {
                emit(Resource.Success((response as CountriesResponse).countriesList))
            } else {
                emit(Resource.Error(countriesErrorMessage))
            }
        }

    override suspend fun getVacancySuggestions(textForSuggestions: String): Flow<Resource<List<VacancyFunctTitle>>> =
        flow {
            if (textForSuggestions.length
                in MIN_VACANCY_SUGGESTION_REQUEST_TEXT_LENGTH..MAX_VACANCY_SUGGESTION_REQUEST_TEXT_LENGTH
            ) {
                val response = client.doRequest(
                    HeadHunterRequest.VacancySuggestions(
                        textForSuggestions
                    )
                )
                if (response.resultCode == Response.SUCCESS) {
                    emit(Resource.Success((response as VacancySuggestionsResponse).vacancyPositionsList))
                } else {
                    emit(Resource.Error(vacancySuggestionsErrorMessage))
                }
            } else {
                emit(Resource.Error(vacancySuggestionsRequestLengthErrorMessage))
            }
        }

    override suspend fun searchVacancy(
        textForSearch: String,
        areaId: String?,
        industryIds: List<String>?,
        currencyCode: String?,
        salary: Int?,
        withSalaryOnly: Boolean,
        page: Int?,
        perPage: Int?,
    ): Flow<Resource<VacancyListResponse>> = flow {
        val response = client.doRequest(
            HeadHunterRequest.VacancySearch(
                textForSearch = textForSearch,
                areaId = areaId,
                industryIds = industryIds,
                currencyCode = currencyCode,
                salary = salary,
                withSalaryOnly = withSalaryOnly,
                page = page,
                perPage = perPage,
            )
        )
        when (response.resultCode) {
            Response.SUCCESS -> emit(Resource.Success(response as VacancyListResponse))
            Response.NO_INTERNET -> emit(Resource.InternetConnectionError(timeoutErrorMessage))
            else -> emit(Resource.Error(vacancySearchErrorMessage))
        }
    }

    override suspend fun getVacancyById(id: String): Flow<Resource<VacancyDetails>> =
        flow {
            val response =
                client.doRequest(HeadHunterRequest.VacancyById(id))
            when (response.resultCode) {
                Response.SUCCESS -> {
                    val data: VacancyByIdResponse = response as VacancyByIdResponse
                    emit(Resource.Success(data.mapToDomain()))
                }

                Response.NO_INTERNET -> emit(Resource.InternetConnectionError(vacancyGetByIdErrorMessage))
                Response.NOT_FOUND -> emit(Resource.NotFoundError(vacancyGetByIdErrorMessage))
                else -> emit(Resource.Error(vacancyGetByIdErrorMessage))
            }
        }
}
