package com.spyker3d.easyjob.favorites.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import com.spyker3d.easyjob.db.data.db.AppDatabase
import com.spyker3d.easyjob.db.data.db.VacancyDbConvertor
import com.spyker3d.easyjob.details.domain.model.VacancyDetails
import com.spyker3d.easyjob.favorites.domain.api.LocalRepository
import com.spyker3d.easyjob.utils.Resource
import java.io.UncheckedIOException

class FavoritesRepositoryRoomBased(
    private val appDatabase: AppDatabase,
    private val vacancyDbConvertor: VacancyDbConvertor
) : LocalRepository {
    override suspend fun getFavoritesVacancyList(): Flow<Resource<List<VacancyDetails>>> {
        return flow {
            try {
                appDatabase.vacancyDao()
                    .getAllVacancyFlow()
                    .map { list ->
                        list.map {
                            vacancyDbConvertor.mapVacancy(it)
                        }
                    }
                    .collect {
                        emit(Resource.Success(it))
                    }
            } catch (e: UncheckedIOException) {
                emit(Resource.Error(message = e.message.toString()))
            }

        }
    }
}
