package com.spyker3d.easyjob.search.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.spyker3d.easyjob.network.data.dto.linked.VacancyFunctTitle
import com.spyker3d.easyjob.network.data.mapper.vacancyFuncTitleListToListForSuggestions
import com.spyker3d.easyjob.search.domain.api.GetSuggestionsForSearchUseCase
import com.spyker3d.easyjob.search.domain.api.SearchRepository
import com.spyker3d.easyjob.utils.Resource

class GetSuggestionsForSearchUseCaseImpl(private val repository: SearchRepository) : GetSuggestionsForSearchUseCase {
    override suspend fun execute(textOfRequest: String): Flow<List<String>> {
        return flow {
            repository
                .getVacancySuggestions(textOfRequest)
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            emit(vacancyFuncTitleListToListForSuggestions(resource.data as List<VacancyFunctTitle>))
                        }

                        is Resource.Error, is Resource.InternetConnectionError, is Resource.NotFoundError -> {
                            emit(emptyList())
                        }
                    }
                }
        }
    }
}
