package com.spyker3d.easyjob.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import com.spyker3d.easyjob.details.presentation.VacancyDetailsViewModel
import com.spyker3d.easyjob.favorites.presentation.FavoritesViewModel
import com.spyker3d.easyjob.filter.presentation.viewmodel.CountryFilterViewModel
import com.spyker3d.easyjob.filter.presentation.viewmodel.FilterIndustryViewModel
import com.spyker3d.easyjob.filter.presentation.viewmodel.FilterSettingsViewModel
import com.spyker3d.easyjob.filter.presentation.viewmodel.PlaceToWorkFilterViewModel
import com.spyker3d.easyjob.filter.presentation.viewmodel.RegionFilterViewModel
import com.spyker3d.easyjob.search.presentation.SearchViewModel

val viewModelModule = module {
    viewModel<VacancyDetailsViewModel> { (vacancyId: String) ->
        VacancyDetailsViewModel(
            application = androidApplication(),
            getVacancyDetailsUseCase = get(),
            navigatorInteractor = get(),
            detailsDbInteractor = get(),
            vacancyId = get { parametersOf(vacancyId) }
        )
    }

    viewModel<SearchViewModel> {
        SearchViewModel(
            interactor = get(),
            getSuggestsUseCase = get(),
            getFilterUseCase = get(),
        )
    }
    viewModel<FavoritesViewModel> {
        FavoritesViewModel(getFavoritesListUseCase = get())
    }

    viewModel<CountryFilterViewModel> {
        CountryFilterViewModel(countryFilterInteractor = get())
    }

    viewModel<RegionFilterViewModel> {
        RegionFilterViewModel(regionFilterInteractor = get(), countryFilterInteractor = get())
    }

    viewModel<FilterSettingsViewModel> {
        FilterSettingsViewModel(filterStorage = get())
    }

    viewModel<FilterIndustryViewModel> {
        FilterIndustryViewModel(
            filterStorage = get(),
            filterDictionaries = get(),
            networkStatus = get()
        )
    }

    viewModel<PlaceToWorkFilterViewModel> {
        PlaceToWorkFilterViewModel(placeToWorkFilterInteractor = get())
    }
}
