package com.spyker3d.easyjob.favorites.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.spyker3d.easyjob.R
import com.spyker3d.easyjob.details.domain.model.VacancyDetails
import com.spyker3d.easyjob.details.ui.JobDetailsFragment
import com.spyker3d.easyjob.favorites.domain.api.GetFavoritesListUseCase
import com.spyker3d.easyjob.utils.Resource

class FavoritesViewModel(private val getFavoritesListUseCase: GetFavoritesListUseCase) : ViewModel() {
    private val mutableStateLiveData = MutableLiveData<FavoritesListState>(FavoritesListState.Empty)
    val stateToObserve: LiveData<FavoritesListState> = mutableStateLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            runDataInteraction()
        }
    }

    fun openForDetails(vacancyId: String, navController: NavController) {
        navController.navigate(
            R.id.action_favoritesJobsFragment_to_jobDetailsFragment,
            JobDetailsFragment.createArgs(vacancyId)
        )
    }

    private suspend fun runDataInteraction() {
        getFavoritesListUseCase.execute()
            .collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        with(resource.data as List<VacancyDetails>) {
                            if (this.isEmpty()) {
                                mutableStateLiveData.postValue(FavoritesListState.Empty)
                            } else {
                                mutableStateLiveData.postValue(FavoritesListState.Success(this))
                            }
                        }
                    }

                    else -> mutableStateLiveData.postValue(FavoritesListState.Error)
                }
            }
    }
}
