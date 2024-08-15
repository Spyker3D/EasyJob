package com.spyker3d.easyjob.favorites.ui

import com.spyker3d.easyjob.details.domain.model.VacancyDetails

fun interface ClickerForVacancyDetail {
    fun onItemClick(vacancy: VacancyDetails)
}
