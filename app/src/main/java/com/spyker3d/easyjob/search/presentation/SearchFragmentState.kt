package com.spyker3d.easyjob.search.presentation

import com.spyker3d.easyjob.search.domain.model.Vacancy

sealed class SearchFragmentState {
    data class SearchVacancy(
        val searchVacancy: List<Vacancy>,
        val totalFoundVacancy: Int,
        val isLastPage: Boolean,
        val isFirstPage: Boolean
    ) : SearchFragmentState()

    data class ServerError(
        val message: String
    ) : SearchFragmentState()

    data object NoResult : SearchFragmentState()
    data object Loading : SearchFragmentState()
    data object LoadingNewPage : SearchFragmentState()
    data object NoTextInInputEditText : SearchFragmentState()
}
