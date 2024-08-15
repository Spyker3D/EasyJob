package com.spyker3d.easyjob.search.domain.api

import com.spyker3d.easyjob.search.domain.model.SearchParameters

fun interface GetFilterUseCase {
    fun execute(): SearchParameters?
}
