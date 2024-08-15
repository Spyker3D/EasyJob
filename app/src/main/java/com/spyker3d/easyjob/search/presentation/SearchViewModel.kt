package com.spyker3d.easyjob.search.presentation

import android.database.CursorIndexOutOfBoundsException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.spyker3d.easyjob.search.domain.api.GetFilterUseCase
import com.spyker3d.easyjob.search.domain.api.GetSuggestionsForSearchUseCase
import com.spyker3d.easyjob.search.domain.api.SearchInteractor
import com.spyker3d.easyjob.search.domain.model.SearchParameters
import com.spyker3d.easyjob.search.domain.model.Vacancy
import com.spyker3d.easyjob.utils.debounce

private const val SEARCH_DEBOUNCE_DELAY = 2000L
private const val CLICK_DEBOUNCE_DELAY = 1000L
private const val PER_PAGE = 20
private const val SUGGESTIONS_DEBOUNCE_DELAY = 300L

class SearchViewModel(
    private val interactor: SearchInteractor,
    private val getSuggestsUseCase: GetSuggestionsForSearchUseCase,
    private val getFilterUseCase: GetFilterUseCase,
) : ViewModel() {
    private val searchLiveData =
        MutableLiveData<SearchFragmentState>(SearchFragmentState.NoTextInInputEditText)
    private var latestSearchText: String? = null
    private var autoSearchDelayJob: Job? = null
    private var searchJob: Job? = null
    private var suggestionsIncomeJob: Job? = null
    private var isClickAllowed = true
    private var suggestionsList = MutableLiveData<List<String>>(emptyList())
    val suggestionsLivaData: LiveData<List<String>> = suggestionsList
    private val filterIsOn = MutableLiveData(false)
    val filterStateToObserve: LiveData<Boolean> = filterIsOn
    private var parametersForSearch: SearchParameters? = null
    var currentPage = 0
    private var pagesCount = 0
    private val vacanciesList = mutableListOf<Vacancy>()
    private var totalFound = 0
    private var isLastCapitalOfInputSearched = false
    private var suggestionsRequestDebounced: ((String) -> Unit)? = null
    private var lastSuggestionsRequestText = String()

    init {
        suggestionsRequestDebounced = debounce(
            SUGGESTIONS_DEBOUNCE_DELAY, viewModelScope, true
        ) { textForSuggestions ->
            requestSuggestionsForSearch(textForSuggestions)
        }
    }

    fun checkFilterStatus() {
        parametersForSearch = getFilterUseCase.execute()
        filterIsOn.postValue(parametersForSearch != null)
    }

    fun getSuggestionsForSearch(textForSuggests: String) {
        if (textForSuggests == lastSuggestionsRequestText) return
        if (suggestionsRequestDebounced != null) suggestionsRequestDebounced?.invoke(textForSuggests)
    }

    private fun requestSuggestionsForSearch(textForSuggests: String) {
        suggestionsIncomeJob?.cancel()
        lastSuggestionsRequestText = textForSuggests
        suggestionsIncomeJob = viewModelScope.launch {
            getSuggestsUseCase.execute(textForSuggests)
                .collect {
                    suggestionsList.postValue(it)
                }
        }
    }

    fun fragmentStateLiveData(): LiveData<SearchFragmentState> = searchLiveData
    fun updateState(state: SearchFragmentState) {
        searchLiveData.postValue(state)
    }

    private fun updateState(
        searchVacancy: List<Vacancy>,
        totalFoundVacancy: Int,
        isLastPage: Boolean,
        isFirstPage: Boolean,
    ) {
        searchLiveData.postValue(
            when (searchLiveData.value) {
                is SearchFragmentState.SearchVacancy -> {
                    (searchLiveData.value as SearchFragmentState.SearchVacancy)
                        .copy(
                            searchVacancy = searchVacancy,
                            totalFoundVacancy = totalFoundVacancy,
                            isLastPage = isLastPage,
                            isFirstPage = isFirstPage
                        )
                }

                else -> {
                    SearchFragmentState.SearchVacancy(
                        searchVacancy = searchVacancy,
                        totalFoundVacancy = totalFoundVacancy,
                        isLastPage = isLastPage,
                        isFirstPage = isFirstPage
                    )
                }
            }
        )
    }

    private fun showProgressIndicator(page: Int) {
        if (page == 0) {
            updateState(SearchFragmentState.Loading)
        } else {
            updateState(SearchFragmentState.LoadingNewPage)
        }
    }

    private fun searchResult(text: String?) {
        if (text.isNullOrBlank()) return
        searchJob?.cancel()
        showProgressIndicator(currentPage)
        searchJob = viewModelScope.launch {
            interactor
                .searchVacancy(text, parametersForSearch, PER_PAGE, currentPage)
                .collect { vacancy ->

                    when {
                        vacancy.result!!.isNotEmpty() -> {
                            pagesCount = vacancy.pages
                            totalFound = vacancy.foundVacancy
                            if (currentPage == pagesCount - 1 || vacanciesList.count() == vacancy.foundVacancy) {
                                vacanciesList.addAll(vacancy.result)
                                updateState(
                                    searchVacancy = vacanciesList,
                                    totalFoundVacancy = vacancy.foundVacancy,
                                    isLastPage = true,
                                    isFirstPage = vacancy.page == 0
                                )
                            } else {
                                vacanciesList += vacancy.result
                                updateState(
                                    searchVacancy = vacanciesList,
                                    totalFoundVacancy = vacancy.foundVacancy,
                                    isLastPage = pagesCount == 1,
                                    isFirstPage = vacancy.page == 0
                                )
                            }
                        }

                        vacancy.errorMessage!!.isNotEmpty() -> {
                            updateState(SearchFragmentState.ServerError(vacancy.errorMessage))
                        }

                        else -> updateState(SearchFragmentState.NoResult)
                    }
                }
        }
    }

    fun searchImmediately(text: String) {
        vacanciesList.clear()
        autoSearchDelayJob?.cancel()
        searchResult(text)
    }

    fun repeatSearch() {
        if (!latestSearchText.isNullOrEmpty()) searchImmediately(latestSearchText!!)
    }

    fun searchWithDebounce(text: String?) {
        if (text == latestSearchText) return
        if (text?.isBlank() == true) {
            autoSearchDelayJob?.cancel()
            latestSearchText = text
            searchLiveData.postValue(SearchFragmentState.NoTextInInputEditText)
            isLastCapitalOfInputSearched = true
        } else {
            isLastCapitalOfInputSearched = false
            vacanciesList.clear()
            latestSearchText = text
            autoSearchDelayJob?.cancel()
            autoSearchDelayJob = viewModelScope.launch {
                delay(SEARCH_DEBOUNCE_DELAY)
                searchResult(text)
            }
        }
    }

    fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewModelScope.launch {
                delay(CLICK_DEBOUNCE_DELAY)
                isClickAllowed = true
            }
        }
        return current
    }

    fun onLastItemReached() {
        if (searchJob?.isActive == true) return
        try {
            if (currentPage < pagesCount - 1 && searchJob?.isActive == false) {
                currentPage++
                autoSearchDelayJob?.cancel()
                autoSearchDelayJob = viewModelScope.launch {
                    searchResult(latestSearchText!!)
                }
            } else {
                updateState(
                    SearchFragmentState.SearchVacancy(
                        vacanciesList,
                        totalFound,
                        isLastPage = true,
                        isFirstPage = false
                    )
                )
            }
        } catch (e: CursorIndexOutOfBoundsException) {
            updateState(SearchFragmentState.ServerError(e.message.toString()))
        }
    }

    fun stopAutoSearch() {
        autoSearchDelayJob?.cancel()
    }

}
