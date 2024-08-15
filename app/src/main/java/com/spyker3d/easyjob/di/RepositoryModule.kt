package com.spyker3d.easyjob.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import com.spyker3d.easyjob.db.data.db.VacancyDbConvertor
import com.spyker3d.easyjob.db.data.repository.VacancyRepositoryImpl
import com.spyker3d.easyjob.search.domain.api.VacancyRepository
import com.spyker3d.easyjob.details.data.repository.NavigatorRepositoryImpl
import com.spyker3d.easyjob.details.domain.api.NavigatorRepository
import com.spyker3d.easyjob.details.domain.api.VacancyDetailsRepository
import com.spyker3d.easyjob.favorites.data.repository.FavoritesRepositoryRoomBased
import com.spyker3d.easyjob.favorites.domain.api.LocalRepository
import com.spyker3d.easyjob.filter.data.repository.FilterDictionariesRepositoryHHNetworkClientBased
import com.spyker3d.easyjob.filter.domain.api.FilterDictionariesRepository
import com.spyker3d.easyjob.filter.data.repository.FilterStorageRepositoryImpl
import com.spyker3d.easyjob.filter.domain.api.FilterStorageRepository
import com.spyker3d.easyjob.network.data.repository.HeadHunterRepository
import com.spyker3d.easyjob.search.domain.api.SearchRepository

val repositoryModule = module {
    single<NavigatorRepository> {
        NavigatorRepositoryImpl(externalNavigator = get())
    }

    single<FilterStorageRepository> {
        FilterStorageRepositoryImpl(filterStorage = get())
    }

    factory<SearchRepository> {
        HeadHunterRepository(client = get(), context = androidContext())
    }

    factory { VacancyDbConvertor() }

    single<VacancyRepository> {
        VacancyRepositoryImpl(appDatabase = get(), vacancyDbConvertor = get())
    }

    factory<VacancyDetailsRepository> {
        HeadHunterRepository(client = get(), context = androidContext())
    }

    factory<LocalRepository> {
        FavoritesRepositoryRoomBased(appDatabase = get(), vacancyDbConvertor = get())
    }

    factory<FilterDictionariesRepository> {
        FilterDictionariesRepositoryHHNetworkClientBased(client = get(), context = androidContext())
    }
}
