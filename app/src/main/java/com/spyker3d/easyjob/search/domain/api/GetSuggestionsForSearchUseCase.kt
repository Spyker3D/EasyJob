package com.spyker3d.easyjob.search.domain.api

import kotlinx.coroutines.flow.Flow

fun interface GetSuggestionsForSearchUseCase {
    suspend fun execute(textOfRequest: String): Flow<List<String>>
}
