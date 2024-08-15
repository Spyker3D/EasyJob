package com.spyker3d.easyjob.di

import org.koin.dsl.module
import com.spyker3d.easyjob.details.domain.api.DetailsDbInteractor
import com.spyker3d.easyjob.details.domain.api.GetVacancyDetailsUseCase
import com.spyker3d.easyjob.details.domain.api.NavigatorInteractor
import com.spyker3d.easyjob.details.domain.impl.DetailsDbInteractorImpl
import com.spyker3d.easyjob.details.domain.impl.GetVacancyDetailsUseCaseImpl
import com.spyker3d.easyjob.details.domain.impl.NavigatorInteractorImpl
import com.spyker3d.easyjob.favorites.domain.api.FavoriteDbInteractor
import com.spyker3d.easyjob.favorites.domain.api.GetFavoritesListUseCase
import com.spyker3d.easyjob.favorites.domain.impl.FavoriteDbInteractorImpl
import com.spyker3d.easyjob.favorites.domain.impl.GetFavoritesListImpl
import com.spyker3d.easyjob.filter.domain.api.CountryFilterInteractor
import com.spyker3d.easyjob.filter.domain.api.FilterDictionariesInteractor
import com.spyker3d.easyjob.filter.domain.api.FilterStorageInteractor
import com.spyker3d.easyjob.filter.domain.api.PlaceToWorkFilterInteractor
import com.spyker3d.easyjob.filter.domain.api.RegionFilterInteractor
import com.spyker3d.easyjob.filter.domain.impl.CountryFilterInteractorImpl
import com.spyker3d.easyjob.filter.domain.impl.FilterDictionariesInteractorImpl
import com.spyker3d.easyjob.filter.domain.impl.FilterStorageInteractorImpl
import com.spyker3d.easyjob.filter.domain.impl.PlaceToWorkFilterInteractorImpl
import com.spyker3d.easyjob.filter.domain.impl.RegionFilterInteractorImpl
import com.spyker3d.easyjob.search.domain.impl.GetFilterUseCaseImpl
import com.spyker3d.easyjob.search.domain.impl.GetSuggestionsForSearchUseCaseImpl
import com.spyker3d.easyjob.search.domain.impl.SearchInteractorImpl
import com.spyker3d.easyjob.search.domain.api.GetFilterUseCase
import com.spyker3d.easyjob.search.domain.api.GetSuggestionsForSearchUseCase
import com.spyker3d.easyjob.search.domain.api.SearchInteractor

val interactorModule = module {
    factory<NavigatorInteractor> {
        NavigatorInteractorImpl(navigatorRepository = get())
    }

    factory<GetSuggestionsForSearchUseCase> {
        GetSuggestionsForSearchUseCaseImpl(repository = get())
    }

    factory<SearchInteractor> {
        SearchInteractorImpl(repository = get(), converter = get())
    }

    factory<GetVacancyDetailsUseCase> {
        GetVacancyDetailsUseCaseImpl(vacancyDetailsRepository = get())
    }

    factory<DetailsDbInteractor> {
        DetailsDbInteractorImpl(vacancyRepository = get())
    }

    factory<FavoriteDbInteractor> {
        FavoriteDbInteractorImpl(vacancyRepository = get())
    }

    factory<GetFavoritesListUseCase> {
        GetFavoritesListImpl(repository = get())
    }

    factory<FilterDictionariesInteractor> {
        FilterDictionariesInteractorImpl(repository = get())
    }

    factory<FilterStorageInteractor> {
        FilterStorageInteractorImpl(repository = get())
    }

    factory<CountryFilterInteractor> {
        CountryFilterInteractorImpl(
            filterStorageRepository = get(),
            filterDictionariesRepository = get(),
            placeToWorkFilterInteractor = get()
        )
    }

    factory<RegionFilterInteractor> {
        RegionFilterInteractorImpl(filterStorageRepository = get(), filterDictionariesRepository = get())
    }

    factory<PlaceToWorkFilterInteractor> {
        PlaceToWorkFilterInteractorImpl(filterStorageRepository = get(), filterDictionariesRepository = get())
    }
    factory<GetFilterUseCase> {
        GetFilterUseCaseImpl(filterRepository = get())
    }
}
