package com.spyker3d.easyjob.network.data.mapper

import com.spyker3d.easyjob.network.data.dto.linked.VacancyFunctTitle

fun vacancyFuncTitleListToListForSuggestions(listOfVacancyTitles: List<VacancyFunctTitle>): List<String> {
    val listOfSuggestions = mutableListOf<String>()
    listOfVacancyTitles.forEach { vacancyFunctTitle ->
        listOfSuggestions.add(vacancyFunctTitle.text)
    }
    return listOfSuggestions.toList()
}
