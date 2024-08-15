package com.spyker3d.easyjob.utils

import com.spyker3d.easyjob.details.domain.model.AreaDetails
import com.spyker3d.easyjob.details.domain.model.EmployerInfo
import com.spyker3d.easyjob.details.domain.model.JobInfo
import com.spyker3d.easyjob.details.domain.model.LogoUrlsDetails
import com.spyker3d.easyjob.details.domain.model.SalaryDetails
import com.spyker3d.easyjob.details.domain.model.VacancyDetails
import com.spyker3d.easyjob.filter.domain.model.Area
import com.spyker3d.easyjob.filter.domain.model.Country
import com.spyker3d.easyjob.filter.domain.model.Industry
import com.spyker3d.easyjob.filter.domain.model.SphereOfIndustry

object Mock {
    val vacancyDetailsMok = VacancyDetails(
        id = "102145630",
        name = "Стропальщик",
        description = "<p>Вахтовый метод работы в г. Ленск</p> <p> </p> <p><strong>Обязанности:" +
            "</strong></p> <p>* Строповка тяжелых грузов;<br />* Строповка и увязка простых изделий, деталей, ",
        employerInfo = EmployerInfo(
            employerName = "Алмаздортранс",
            contacts = null,
            area = AreaDetails(
                id = "68",
                name = "Омск"
            ),
            logo = LogoUrlsDetails(
                size90 = "https://img.hhcdn.ru/employer-logo/3932303.png",
                size240 = "https://img.hhcdn.ru/employer-logo/3932304.png",
                raw = "https://img.hhcdn.ru/employer-logo-original/872857.png"
            )
        ),
        jobInfo = JobInfo(
            salary = SalaryDetails(
                currency = "RUR",
                from = 100_000,
                gross = true,
                to = null,
            ),
            experience = "between1And3",
            employment = "Полная занятость",
            keySkills = listOf("skill", "SKILLL")
        ),
        vacancyUrl = "https://api.hh.ru/vacancies/101684044?locale=RU&host=hh.ru"
    )
    val areas = listOf(
        Area(
            id = "113",
            parentId = null,
            name = "Россия",
            subAreas = listOf(
                Area(
                    id = "1620",
                    parentId = "113",
                    name = "Республика Марий Эл",
                    subAreas = listOf(
                        Area(
                            id = "4228",
                            parentId = "1620",
                            name = "Виловатово",
                            subAreas = null
                        ),
                        Area(
                            id = "1621",
                            parentId = "1620",
                            name = "Волжск",
                            subAreas = null
                        ),
                        Area(
                            id = "1622",
                            parentId = "1620",
                            name = "Звенигово",
                            subAreas = null
                        )
                    )
                ),
                Area(
                    id = "1624",
                    parentId = "113",
                    name = "Республика Татарстан",
                    subAreas = listOf(
                        Area(
                            id = "1625",
                            parentId = "1624",
                            name = "Агрыз",
                            subAreas = null
                        ),
                        Area(
                            id = "1626",
                            parentId = "1624",
                            name = "Азнакаево",
                            subAreas = null
                        ),
                        Area(
                            id = "4167",
                            parentId = "1624",
                            name = "Айша",
                            subAreas = null
                        ),
                        Area(
                            id = "7193",
                            parentId = "1624",
                            name = "Аккузово",
                            subAreas = null
                        )
                    )
                )
            )
        ),
        Area(
            id = "1001",
            parentId = null,
            name = "Другие регионы",
            subAreas = listOf(
                Area(
                    id = "2112",
                    parentId = "1001",
                    name = "Абхазия",
                    subAreas = null
                ),
                Area(
                    id = "306",
                    parentId = "1001",
                    name = "Буркина Фасо",
                    subAreas = null
                ),
                Area(
                    id = "21",
                    parentId = "1001",
                    name = "Великобритания",
                    subAreas = null
                ),
                Area(
                    id = "114",
                    parentId = "1001",
                    name = "Венгрия",
                    subAreas = null
                ),
                Area(
                    id = "62",
                    parentId = "1001",
                    name = "Молдова",
                    subAreas = listOf(
                        Area(
                            id = "5049",
                            parentId = "62",
                            name = "Кишинёв",
                            subAreas = null
                        ),
                        Area(
                            id = "11285",
                            parentId = "62",
                            name = "Тирасполь",
                            subAreas = null
                        )
                    )
                ),
                Area(
                    id = "2440",
                    parentId = "1001",
                    name = "Ямайка",
                    subAreas = null
                ),
                Area(
                    id = "111",
                    parentId = "1001",
                    name = "Япония",
                    subAreas = null
                )
            )
        ),
        Area(
            id = "97",
            parentId = null,
            name = "Узбекистан",
            subAreas = listOf(
                Area(
                    id = "2763",
                    parentId = "97",
                    name = "Аккурган",
                    subAreas = null
                ),
                Area(
                    id = "2764",
                    parentId = "97",
                    name = "Акташ (Узбекистан)",
                    subAreas = null
                ),
                Area(
                    id = "2765",
                    parentId = "97",
                    name = "Алат (Узбекистан)",
                    subAreas = null
                )
            )
        )
    )
    val countries = listOf(
        Country(
            id = "113",
            name = "Россия",
            url = "https://api.hh.ru/areas/113?locale=RU&host=hh.ru"
        ),
        Country(
            id = "2112",
            name = "Абхазия",
            url = "https://api.hh.ru/areas/2112?locale=RU&host=hh.ru"
        ),
        Country(
            id = "5",
            name = "Украина",
            url = "https://api.hh.ru/areas/5?locale=RU&host=hh.ru"
        ),
        Country(
            id = "6",
            name = "Австралия",
            url = "https://api.hh.ru/areas/6?locale=RU&host=hh.ru"
        ),
        Country(
            id = "40",
            name = "Казахстан",
            url = "https://api.hh.ru/areas/40?locale=RU&host=hh.ru"
        ),
        Country(
            id = "6868",
            name = "Другое",
            url = "https://api.hh.ru/areas/6868?locale=RU&host=hh.ru"
        ),
        Country(
            id = "2440",
            name = "Ямайка",
            url = "https://api.hh.ru/areas/2440?locale=RU&host=hh.ru"
        ),
        Country(
            id = "111",
            name = "Япония",
            url = "https://api.hh.ru/areas/111?locale=RU&host=hh.ru"
        )
    )
    val industries = listOf(
        Industry(
            id = "5",
            name = "Перевозки, логистика, склад, ВЭД",
            industries = listOf(
                SphereOfIndustry(
                    id = "5.461",
                    name = "Авиаперевозки"
                ),
                SphereOfIndustry(
                    id = "5.462",
                    name = "Автомобильные перевозки"
                ),
                SphereOfIndustry(
                    id = "5.463",
                    name = "Железнодорожные перевозки"
                ),
                SphereOfIndustry(
                    id = "5.464",
                    name = "Морские, речные перевозки"
                ),
                SphereOfIndustry(
                    id = "5.465",
                    name = "Транспортно-логистические комплексы, порты (воздушный, водный, железнодорожный)"
                ),
                SphereOfIndustry(
                    id = "5.466",
                    name = "Складские услуги"
                ),
                SphereOfIndustry(
                    id = "5.467",
                    name = "Курьерская, почтовая доставка"
                ),
                SphereOfIndustry(
                    id = "5.468",
                    name = "ВЭД, таможенное оформление"
                )
            )
        ),
        Industry(
            id = "7",
            name = "Информационные технологии, системная интеграция, интернет",
            industries = listOf(
                SphereOfIndustry(
                    id = "7.538",
                    name = "Интернет-провайдер"
                ),
                SphereOfIndustry(
                    id = "7.539",
                    name = "Системная интеграция, ИТ-консалтинг"
                ),
                SphereOfIndustry(
                    id = "7.540",
                    name = "Разработка программного обеспечения"
                ),
                SphereOfIndustry(
                    id = "7.541",
                    name = "Интернет-компания (поисковики, платежные системы и прочее)"
                )
            )
        ),
        Industry(
            id = "9",
            name = "Телекоммуникации, связь",
            industries = listOf(
                SphereOfIndustry(
                    id = "9.399",
                    name = "Мобильная связь"
                ),
                SphereOfIndustry(
                    id = "9.400",
                    name = "Фиксированная связь"
                ),
                SphereOfIndustry(
                    id = "9.401",
                    name = "Оптоволоконная связь"
                ),
                SphereOfIndustry(
                    id = "9.402",
                    name = "Спутниковая связь"
                )
            )
        ),
        Industry(
            id = "11",
            name = "СМИ, маркетинг, реклама, BTL, PR, дизайн, продюсирование",
            industries = listOf(
                SphereOfIndustry(
                    id = "11.452",
                    name = "Издательская деятельность"
                ),
                SphereOfIndustry(
                    id = "11.453",
                    name = "Производство мультимедиа, контента, редакторская деятельность"
                ),
                SphereOfIndustry(
                    id = "11.454",
                    name = "Распространение телепрограмм, кино (кабельное телевидение)"
                ),
                SphereOfIndustry(
                    id = "11.455",
                    name = "Киностудии и студии звукозаписи"
                ),
                SphereOfIndustry(
                    id = "11.456",
                    name = "Теле- и радиовещание"
                ),
                SphereOfIndustry(
                    id = "11.457",
                    name = "Продюсерский центр"
                ),
                SphereOfIndustry(
                    id = "11.458",
                    name = "Распространение мультимедиа и печатной продукции"
                ),
                SphereOfIndustry(
                    id = "11.459",
                    name = "Маркетинговые, рекламные, BTL, дизайнерские, Event-, PR-агентства, организация выставок"
                ),
                SphereOfIndustry(
                    id = "11.460",
                    name = "Производство печатной, полиграфической продукции"
                ),
                SphereOfIndustry(
                    id = "11.685",
                    name = "Производство и продажа рекламно-сувенирной продукции"
                )
            )
        ),
        Industry(
            id = "43",
            name = "Финансовый сектор",
            industries = listOf(
                SphereOfIndustry(
                    id = "43.641",
                    name = "Аудит, управленческий учет, финансово-юридический консалтинг"
                ),
                SphereOfIndustry(
                    id = "43.642",
                    name = "Услуги по ведению бухгалтерского и налогового учета, расчет заработной платы"
                ),
                SphereOfIndustry(
                    id = "43.643",
                    name = "Коллекторская деятельность"
                ),
                SphereOfIndustry(
                    id = "43.644",
                    name = "Лизинговые компании"
                ),
                SphereOfIndustry(
                    id = "43.645",
                    name = "НПФ"
                ),
                SphereOfIndustry(
                    id = "43.646",
                    name = "Страхование, перестрахование"
                ),
                SphereOfIndustry(
                    id = "43.647",
                    name = "Банк"
                ),
                SphereOfIndustry(
                    id = "43.648",
                    name = "Управляющая, инвестиционная компания (управление активами)"
                ),
                SphereOfIndustry(
                    id = "43.649",
                    name = "Факторинговые компании"
                )
            )
        ),
        Industry(
            id = "389",
            name = "Управление многопрофильными активами",
            industries = listOf(
                SphereOfIndustry(
                    id = "389.530",
                    name = "Управляющая компания группы, холдинга, штаб-квартира"
                )
            )
        )
    )
}
