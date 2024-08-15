package com.spyker3d.easyjob.network.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import com.spyker3d.easyjob.network.data.dto.HeadHunterRequest
import com.spyker3d.easyjob.network.data.dto.responses.AreasResponse
import com.spyker3d.easyjob.network.data.dto.responses.CountriesResponse
import com.spyker3d.easyjob.network.data.dto.responses.IndustryResponse
import com.spyker3d.easyjob.network.data.dto.responses.LocalesResponse
import com.spyker3d.easyjob.network.data.dto.responses.Response
import com.spyker3d.easyjob.network.data.dto.responses.VacancySuggestionsResponse
import com.spyker3d.easyjob.network.data.netapi.HeadHunterApplicationApi
import com.spyker3d.easyjob.network.data.netapi.HeadHunterNetworkClient
import com.spyker3d.easyjob.utils.NetworkStatus
import java.io.UncheckedIOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RetrofitBasedClient(retrofit: Retrofit, private val networkStatus: NetworkStatus) : HeadHunterNetworkClient {
    private val serverService = retrofit.create(HeadHunterApplicationApi::class.java)
    override suspend fun doRequest(request: HeadHunterRequest): Response {
        if (!networkStatus.isConnected()) return Response().apply { resultCode = Response.NO_INTERNET }
        return withContext(Dispatchers.IO) {
            try {
                val response = when (request) {
                    HeadHunterRequest.Locales -> LocalesResponse(
                        localeList = serverService.getLocales()
                    )

                    HeadHunterRequest.Dictionaries -> serverService.getDictionaries()
                    HeadHunterRequest.Industries -> IndustryResponse(
                        industriesList = serverService.getIndustries()
                    )

                    HeadHunterRequest.Areas -> AreasResponse(
                        areasList = serverService.getAreas()
                    )

                    HeadHunterRequest.Counties -> CountriesResponse(
                        countriesList = serverService.getCountries()
                    )

                    is HeadHunterRequest.VacancySuggestions -> VacancySuggestionsResponse(
                        vacancyPositionsList = serverService.getVacancySuggestions(
                            request.textForSuggestions
                        ).vacancyPositionsList
                    )

                    is HeadHunterRequest.VacancySearch -> serverService.searchVacancy(
                        textForSearch = request.textForSearch,
                        areaId = request.areaId,
                        industryIds = request.industryIds,
                        currencyCode = request.currencyCode,
                        salary = request.salary,
                        withSalaryOnly = request.withSalaryOnly,
                        page = request.page,
                        perPage = request.perPage
                    )

                    is HeadHunterRequest.VacancyById -> serverService.getVacancyById(request.id)
                    is HeadHunterRequest.SubAreas -> serverService.getSubAreas(request.areaId)

                    is HeadHunterRequest.SearchInAreas -> serverService.searchInAreas(
                        request.searchText,
                        request.areaId
                    )
                }
                response.apply { resultCode = Response.SUCCESS }
            } catch (e: HttpException) {
                if (e.code() == Response.NOT_FOUND) {
                    Response().apply {
                        errorMessage = e.message
                        resultCode = Response.NOT_FOUND
                    }
                } else {
                    Response().apply {
                        errorMessage = e.message
                        resultCode = Response.FAILURE
                    }
                }
            } catch (e: UncheckedIOException) {
                Response().apply {
                    errorMessage = e.message
                    resultCode = Response.FAILURE
                }
            } catch (e: IllegalStateException) {
                Response().apply {
                    errorMessage = e.message
                    resultCode = Response.ILLEGAL_STATE
                }
            } catch (e: SocketTimeoutException) {
                Response().apply {
                    errorMessage = e.message
                    resultCode = Response.NO_INTERNET
                }
            } catch (e: UnknownHostException) {
                Response().apply {
                    errorMessage = e.message
                    resultCode = Response.FAILURE
                }
            }
        }
    }
}
