# Проект по автоматизации тестовых сценариев для сайта [DemoQA (Книжный магазин)](https://demoqa.com/books)

<div align="center">
<img width="500px" title="Wikipedia" src="media/logo/Bookstore.jpg">
</div>

## :scroll: Содержание:

- [Используемый стек](#computer-используемый-стек)
- [Запуск автотестов](#arrow_forward-запуск-автотестов)
- [Сборка в Jenkins](#-сборка-в-jenkins)
- [Пример Allure-отчета](#-пример-allure-отчета)
- [Интеграция с Allure TestOps](#-интеграция-с-allure-testOps)
- [Уведомления в Telegram](#-уведомления-в-telegram)
- [Видео примера запуска тестов в Selenoid](#-видео-примера-запуска-теста-в-selenoid)

## :computer: Используемый стек

<p align="center">
<a href="https://www.jetbrains.com/idea/"><img width="6%" title="IntelliJ IDEA" src="media/logo/Intelij_IDEA.svg"></a>
<a href="https://www.java.com/"><img width="6%" title="Java" src="media/logo/Java.svg"></a>
<a href="https://selenide.org/"><img width="6%" title="Selenide" src="media/logo/Selenide.svg"></a>
<a href="https://selenoid.autotests.cloud/#/"><img width="6%" title="Selenoid" src="media/logo/Selenoid.svg"></a>
<a href="https://github.com/allure-framework/allure2"><img width="6%" title="Allure Report" src="media/logo/Allure_Report.svg"></a>
<a href="https://qameta.io/"><img width="5%" title="Allure TestOps" src="media/logo/AllureTestOps.svg"></a>
<a href="https://gradle.org/"><img width="6%" title="Gradle" src="media/logo/Gradle.svg"></a>
<a href="https://junit.org/junit5/"><img width="6%" title="JUnit5" src="media/logo/JUnit5.svg"></a>
<a href="https://rest-assured.io/"><img width="5%" title="RestAssured" src="./media/logo/RestAssured.svg"></a>
<a href="https://github.com/"><img width="6%" title="GitHub" src="media/logo/GitHub.svg"></a>
<a href="https://www.jenkins.io/"><img width="6%" title="Jenkins" src="media/logo/Jenkins.svg"></a>
<a href="https://web.telegram.org/"><img width="6%" title="Telegram" src="media/logo/Telegram.svg"></a>
</p>

Автотесты в этом проекте написаны на `Java` с использованием фреймворка `Selenide`.\
`Gradle` - используется как инструмент автоматизации сборки.  \
`JUnit5` - для выполнения тестов.\
`REST Assured` - для тестирования REST-API сервисов.\
`Jenkins` - CI/CD для запуска тестов удаленно.\
`Selenoid` - для удаленного запуска браузера в `Docker` контейнерах.\
`Allure Report` - для визуализации результатов тестирования.\
`Telegram Bot` - для уведомлений о результатах тестирования.\
`Allure TestOps` - как система управления тестированием.

**Особенности проекта**:
- `Page Object` шаблон проектирования
- Различные конфигурации для запуска теста в зависимости от параметров сборки
- Использование библиотеки `Owner`
- Возможность запуска тестов локально или удалённо
- Использование `Faker` для генерации данных
- Использование `Lombok` для моделей в API тестах
- Использование request/response спецификаций для API тестов
- Custom Allure listener для API requests/responses логов
- Возможность запуска тестов напрямую из Allure TestOps
- Автотесты как тестовая документация
- Уведомление о результатах прохождения в Telegram
- По итогу прохождения автотестов генерируется Allure отчет. Содержание отчета:
    - Шаги теста
    - Скриншот страницы на последнем шаге
    - Исходный код страницы в браузере
    - Логи консоли браузера
    - Видео выполнения автотеста

## Реализованные проверки
### Web + Api
- [x] Проверка удаления всех книг из профиля по кнопке "Удалить всё"
- [x] Проверка авторизации после создания пользователя и после его удаления

### Api
- [x] Выполнение успешного запроса на авторизацию
- [x] Выполнение успешного запроса на авторизацию (намеренно пропущен, так как там найден баг)
- [x] Выполнение успешного запроса на создание нового пользователя
- [x] Выполнение неудачного запроса на создание нового пользователя с пустым телом
- [x] Выполнение неудачного запроса на создание нового пользователя с данными существующего пользователя
- [x] Выполнение запроса на удаление пользователя
- [x] Запрос текущего списка книг

## :arrow_forward: Запуск автотестов

Дополнительные свойства извлекаются из соответствующего файла конфигурации (в зависимости от значения `testLaunchType`):
```
./resources/${testLaunchType}.config
```
Логин и пароль пользователя извлекаются из файла конфигурации:
```
./resources/user.config
```

`testLaunchType` - определяет среду для запуска тестов:
>- *local* - для локального запуска (значение по-умолчанию)
>- *remote* - для удаленного запуска

### Запуск тестов из терминала
```
clean test
```
При выполнении данной команды в терминале IDE тесты запустятся локально (значение по-умолчанию).

```
clean test -DtestLaunchType=remote
```
При выполнении данной команды в терминале IDE тесты запустятся удаленно в Docker-контейнере <code>Selenoid</code>.

## <img width="4%" style="vertical-align:middle" title="Jenkins" src="media/logo/Jenkins.svg"> Сборка в <b><a target="_blank" href="https://jenkins.autotests.cloud/job/c36-evded-qa-guru-diploma-api/">Jenkins</a></b>

Для запуска сборки необходимо перейти в раздел <code>Build with Parameters</code> и нажать кнопку <code>Build</code>.
<p align="center">
<img title="Jenkins Build" src="media/screens/jenkins.png">
</p>
После выполнения сборки, в блоке <code>История сборок</code> напротив номера сборки появятся значки <code>Allure Report</code> и <code>Allure TestOps</code>, при клике на которые откроется страница с сформированным html-отчетом и тестовой документацией соответственно.

## <img width="4%" style="vertical-align:middle" title="Allure Report" src="media/logo/Allure_Report.svg"> Пример <b><a target="_blank" href="https://jenkins.autotests.cloud/job/c36-evded-qa-guru-diploma-api/5/allure">Allure-отчета</a></b>
### Overview

Главная страница отчета Allure содержит следующие блоки:

>- <code><strong>*ALLURE REPORT*</strong></code> - отображает дату и время теста, общее количество запущенных тестов, а также диаграмму с процентом и количеством успешных, упавших и сломавшихся в процессе выполнения тестов
>- <code><strong>*TREND*</strong></code> - отображает тенденцию выполнения тестов для всех запусков
>- <code><strong>*SUITES*</strong></code> - отображает распределение тестов по сьютам
>- <code><strong>*CATEGORIES*</strong></code> - отображает распределение неудачных тестов по типам дефектов

<p align="center">
<img title="Allure Overview" src="media/screens/allureMain.png">
</p>

### Список тестов с шагами и тестовыми артефактами
На странице список тестов, сгруппированных по наборам, с указанием статуса для каждого теста.\
Может быть показана полная информация о каждом тесте: теги, продолжительность, подробные шаги.

<p align="center">
  <img src="media/screens/allureSuits.png" alt="AllureSuites" width="750">
</p>

Также доступны дополнительные тестовые артефакты:
>- Screenshot
>- Page Source
>- Browser console log
>- Video

<p align="center">
<img title="Allure Attaches" src="media/screens/allureAttaches.png" width="750">
</p>

## <img width="4%" style="vertical-align:middle" title="Allure TestOps" src="media/logo/AllureTestOps.svg"> Интеграция с <b><a target="_blank" href="https://allure.autotests.cloud/project/4964/dashboards">Allure TestOps</a></b>

Выполнена интеграция сборки <code>Jenkins</code> с <code>Allure TestOps</code>.
Результат выполнения автотестов отображается в <code>Allure TestOps</code>
На Dashboard в <code>Allure TestOps</code> отображена статистика пройденных тестов.

<p align="center">
<img title="Allure TestOps DashBoard" src="media/screens/testops.png" width="750">
</p>

Тест-кейсы в проекте импортируются и постоянно обновляются из кода,
поэтому нет необходимости в синхронизации ручных тест-кейсов и автотестов.\
Достаточно создать и обновить автотест в коде и тест-кейс всегда будет в актуальном состоянии.

### Результат выполнения автотестов в <b><a target="_blank" href="https://allure.autotests.cloud/launch/48835/tree/734119?search=W3siaWQiOiJzdGF0dXMiLCJ0eXBlIjoidGVzdFN0YXR1c0FycmF5IiwidmFsdWUiOlsicGFzc2VkIl19XQ%3D%3D&treeId=0">TestOps</a></b>

<p align="center">
<img title="Test Results in Alure TestOps" src="media/screens/testopsResults.png" width="750">
</p>

## Allure TestOps Test Cases

<p align="center">
  <img src="../qa_guru_HW_diploma_mobile/media/screens/testopsTestCases.png" alt="AllureTestOpsTests" width="750">
</p>

### <img width="4%" style="vertical-align:middle" title="Telegram" src="media/logo/Telegram.svg"> Уведомления в Telegram с использованием бота

После завершения сборки специальный бот, созданный в <code>Telegram</code>, автоматически обрабатывает и отправляет сообщение с отчетом о прогоне тестов.

<p align="center">
<img width="70%" title="Telegram Notifications" src="media/screens/bot.jpg">
</p>

### <img width="4%" style="vertical-align:middle" title="Selenoid" src="media/logo/Selenoid.svg"> Видео примера запуска тестов в Selenoid

В отчетах Allure для каждого теста прикреплено видео прохождения теста
<p align="center">
  <img title="Selenoid Video" src="media/video/tests.gif">
</p>