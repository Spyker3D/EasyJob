package com.spyker3d.easyjob.details.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.spyker3d.easyjob.details.domain.api.DetailsDbInteractor
import com.spyker3d.easyjob.details.domain.api.GetVacancyDetailsUseCase
import com.spyker3d.easyjob.details.domain.api.NavigatorInteractor
import com.spyker3d.easyjob.details.domain.model.VacancyDetails
import com.spyker3d.easyjob.utils.Resource

class VacancyDetailsViewModel(
    application: Application,
    private val getVacancyDetailsUseCase: GetVacancyDetailsUseCase,
    private val navigatorInteractor: NavigatorInteractor,
    private val detailsDbInteractor: DetailsDbInteractor,
    private val vacancyId: String,
) : AndroidViewModel(application) {
    private val _stateLiveData = MutableLiveData<VacancyDetailsState>(VacancyDetailsState.Loading)
    fun getState(): LiveData<VacancyDetailsState> = _stateLiveData


    private var vacancyDetails: VacancyDetails? = null
    private var isFavoriteJob: Job? = null

    init {
        viewModelScope.launch {
            getVacancyDetailsUseCase.execute(vacancyId).collect {
                processSearchVacancyResponse(it)
            }
        }
    }

    private suspend fun processSearchVacancyResponse(searchResult: Resource<VacancyDetails>) {
        if (searchResult.data != null) {
            val vacancyDetailsState = VacancyDetailsState.Content(searchResult.data)
            vacancyDetails = vacancyDetailsState.data
            _stateLiveData.value = vacancyDetailsState
            processFavoriteState(vacancyDetails)
        } else {
            when (searchResult) {
                is Resource.InternetConnectionError -> {
                    var isVacancyInFavorites = false
                    detailsDbInteractor.isExistsVacancy(vacancyId.toInt()).collect { isExists ->
                        isVacancyInFavorites = isExists
                    }
                    if (isVacancyInFavorites) {
                        detailsDbInteractor.getVacancyById(vacancyId.toInt()).collect { vacancyDetails ->
                            _stateLiveData.postValue(VacancyDetailsState.Content(vacancyDetails!!))
                            processFavoriteState(null)
                        }
                    } else {
                        _stateLiveData.postValue(VacancyDetailsState.InternetConnectionError(searchResult.message!!))
                    }
                }

                is Resource.NotFoundError -> {
                    var isVacancyInFavorites = false
                    detailsDbInteractor.isExistsVacancy(vacancyId.toInt()).collect { isExists ->
                        isVacancyInFavorites = isExists
                    }
                    if (isVacancyInFavorites) {
                        detailsDbInteractor.deleteVacancy(vacancyId.toInt())
                    }
                    _stateLiveData.postValue(VacancyDetailsState.Error(searchResult.message!!))
                }

                else -> _stateLiveData.postValue(VacancyDetailsState.ServerError(searchResult.message!!))
            }
        }
    }

    private suspend fun processFavoriteState(vacancyToCheck: VacancyDetails?) {
        detailsDbInteractor.isExistsVacancy(vacancyId.toInt()).collect {
            _stateLiveData.value = VacancyDetailsState.Favorite(it)
            if (vacancyToCheck != null && it) detailsDbInteractor.insertVacancyWithCheck(vacancyToCheck)
        }
    }

    fun onFavoriteClicked() {
        if (isFavoriteJob?.isActive == true) return

        isFavoriteJob = viewModelScope.launch {
            var isInFavoriteList = false
            detailsDbInteractor.isExistsVacancy(vacancyId.toInt()).collect {
                isInFavoriteList = it
            }
            if (isInFavoriteList) {
                detailsDbInteractor.deleteVacancy(vacancyId.toInt())
                _stateLiveData.postValue(VacancyDetailsState.Favorite(false))
            } else {
                if (vacancyDetails != null) {
                    detailsDbInteractor.insertVacancy(vacancyDetails!!)
                    _stateLiveData.postValue(VacancyDetailsState.Favorite(true))
                }
            }
        }
    }

    fun shareVacancy() {
        vacancyDetails?.let { navigatorInteractor.shareLink(it.vacancyUrl) }
    }

    fun dialNumber(number: String) {
        navigatorInteractor.dialNumber(number)
    }

    fun sendEmail(email: String) {
        navigatorInteractor.sendEmail(email)
    }

}
