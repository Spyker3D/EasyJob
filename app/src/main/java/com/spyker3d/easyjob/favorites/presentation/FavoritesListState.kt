package com.spyker3d.easyjob.favorites.presentation

import com.spyker3d.easyjob.details.domain.model.VacancyDetails

sealed class FavoritesListState {
    data object Empty : FavoritesListState()
    data object Error : FavoritesListState()
    data class Success(val listOfFavVacancies: List<VacancyDetails>) : FavoritesListState()
}
