package com.spyker3d.easyjob.filter.data.storage

import com.spyker3d.easyjob.filter.domain.model.FilterGeneral

interface FilterStorage {
    fun saveFinalFilterParameters(filter: FilterGeneral)

    fun saveSpecificFilterParameters(filter: FilterGeneral)

    fun getAllFinalFilterParameters(): FilterGeneral

    fun getAllSavedParameter(): FilterGeneral

    fun clearAllFilterParameters()

    fun clearAllSavedParameters()
}
