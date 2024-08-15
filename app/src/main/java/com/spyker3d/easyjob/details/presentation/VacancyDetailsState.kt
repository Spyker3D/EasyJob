package com.spyker3d.easyjob.details.presentation

import com.spyker3d.easyjob.details.domain.model.VacancyDetails

sealed class VacancyDetailsState {
    data class Content(val data: VacancyDetails) : VacancyDetailsState()
    data class Error(val message: String) : VacancyDetailsState()
    data class InternetConnectionError(val message: String) : VacancyDetailsState()
    data class ServerError(val message: String) : VacancyDetailsState()
    data class Favorite(val isFavorite: Boolean) : VacancyDetailsState()
    data object Loading : VacancyDetailsState()
}
