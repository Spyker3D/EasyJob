package com.spyker3d.easyjob.filter.domain.api

import com.spyker3d.easyjob.filter.domain.model.AreaFilter
import com.spyker3d.easyjob.filter.domain.model.CountryFilter
import com.spyker3d.easyjob.filter.domain.model.FilterGeneral
import com.spyker3d.easyjob.filter.domain.model.IndustryFilter

interface FilterStorageRepository {
    // сохранение параметров для финального фильтра на базовом экране с кнопкой "Применить".
    // После нажатия на кнопку "Применить" сохраненный фильтр потом используется для поиска с фильтром
    fun saveAllFilterParameters(filter: FilterGeneral)

    // получение параметров финального фильтра для экрана поиска
    fun getAllFilterParameters(): FilterGeneral

    // получение параметров для промежуточных фильтров (отрасль/ регион) на экранах с RecyclerView
    // и базового экрана с кнопкой "применить" до нажатия на эту кнопку
    fun getAllSavedParameters(): FilterGeneral

    // сброс параметров для финального фильтра на базовом экране с кнопкой "Применить"
    fun clearAllFilterParameters()

    // проверка для экрана поиска, сохранено ли что-то в финальном фильтре на базовом экране
    fun isFilterActive(): Boolean

    // сохранение параметра региона для параметров промежуточного фильтра на экранах выбора отрасли/региона/места работы
    fun saveArea(area: AreaFilter?)

    // сохранение параметра страны для параметров промежуточного фильтра на экранах выбора отрасли/региона/места работы
    fun saveCountry(country: CountryFilter?)

    // сохранение параметра отрасли для параметров промежуточного фильтра на экранах выбора отрасли/региона/места работы
    fun saveIndustry(industry: IndustryFilter?)

    // сохранение параметра зп для на экране базового фильтра (если "применить" не было нажато)
    fun saveExpectedSalary(salaryAmount: String)

    // сохранение флага "не показывать без зп" на экране базового фильтра (если "применить" не было нажато)
    fun saveHideNoSalaryItems(hideNoSalaryItems: Boolean)
    fun clearAllSavedParameters()
}
