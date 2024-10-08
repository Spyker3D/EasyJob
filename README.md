# EasyJob - приложение по поиску вакансий

Приложение использует HeadHunter API для поиска вакансий (включая их детальное описание) в России, Казахстане, Беларуси 
и в прочих странах, входящих в СНГ. Приложение разрабатывалось командой из 5 человек, список разработчиков указан на 
экране информации о команде разработчиков.

## Общие требования

- Приложение поддерживает устройства, начиная с Android 8.0 (minSdkVersion = 26)
- Приложение поддерживает только портретную ориентацию (`portrait`), при перевороте экрана ориентация не меняется.

## Главный экран -- экран поиска вакансий

На этом экране пользователь может искать вакансии по любому непустому набору слов поискового запроса. Результаты поиска
представляют собой список, содержащий краткую информацию о вакансиях.

### Особенности экрана

- По умолчанию, поиск происходит по всей доступной базе вакансий без учёта региона, отрасли компании и уровня зарплаты и
  валюты.
- Приложение не хранит историю поиска.
- В целях экономии трафика пользователей загрузка результатов поиска происходит постранично по 20
  элементов за раз. Запрос на следующую страницу должен происходит, когда пользователь доскроллил до последнего
  доступного элемента списка.

## Фильтрация -- набор экранов фильтров поиска

Используя настройки фильтра, пользователь может уточнить некоторые параметры поиска, который осуществляется на экране
"Поиск". Фильтр позволяет указать:

- Место работы - регион, населённый пункт, указанный в вакансии как рабочая локация.
- Отрасль - сфера деятельности организации, разместившей вакансию.
- Уровень зарплаты - уровень ЗП, соответствующий указанному в вакансии.
- Возможность скрывать вакансии, для которых не указана ЗП.

### Особенности экранов

Несколько особенностей, которые нужно учитывать при реализации:

- Параметры фильтра не являются обязательными - пользователь может уточнить любой параметр из предложенных, а может не
  указывать ничего. В случае, если указан хотя бы один из параметров он учитывается при последующих поисковых
  запросах на экране "Поиск". Параметры фильтра, которые пользователь не уточнял, в поисковом запросе не участвуют.
- Настройки параметров фильтра сохраняются даже после закрытия приложения.
- Если у пользователя выбрана страна поиска вакансий, то список регионов на экране выбора региона поиска
  ограничивается регионами указанной страны.
- Если пользователь выбрал город до выбора страны, то страна подставляется автоматически.
- Кнопка "Сбросить" появляется, если пользователь указал хотя бы одно значение фильтров.
- Нажатие на кнопку «Применить» на экране фильтра возвращает пользователя на экран поиска. И если поле ввода поискового
  запроса было не пустым, то этот поисковый запрос автоматически выполняется повторно с применением актуальных
  настроек фильтрации.
- Все настройки фильтра сохраняются автоматически сразу после изменения.

## Экран просмотра деталей вакансии

Нажав на элемент списка найденных вакансий (аналогично в списке избранного), пользователь попадает на
экран с подробным описанием вакансии. Помимо уровня ЗП, требуемого опыта и графика работы пользователь может на этом
экране увидеть:

- Информацию о работодателе
- Подробное описание вакансии
- Перечень требуемых ключевых навыков
- Контактную информацию

Также пользователь может поделиться ссылкой на данную вакансию и
связаться с работодателем через указанные контакты.

## Экран избранных вакансий

Пользователь может добавлять вакансии в "Избранное", чтобы иметь возможность быстро вернуться к заинтересовавшему его
предложению. Добавить вакансию в "избранное" (или удалить из "избранного") можно на экране "Вакансия". Все вакансии,
добавленные в закладки, можно увидеть на отдельном экране в приложении.

### Особенности экрана

- Вакансии, добавленные в "избранное" можно просматривать без подключения к интернету. 

## Экран информации о команде разработчиков

На экране отображается статический список людей, участвовавших в разработке приложения. 
