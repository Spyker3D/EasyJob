package com.spyker3d.easyjob.search.domain.impl

import com.spyker3d.easyjob.filter.domain.api.FilterStorageRepository
import com.spyker3d.easyjob.search.data.mapper.SearchVacancyConverter
import com.spyker3d.easyjob.search.domain.api.GetFilterUseCase
import com.spyker3d.easyjob.search.domain.model.SearchParameters

class GetFilterUseCaseImpl(private val filterRepository: FilterStorageRepository) : GetFilterUseCase {
    override fun execute(): SearchParameters? {
        return SearchVacancyConverter().toSearchParameters(filterRepository.getAllFilterParameters())
    }
}
