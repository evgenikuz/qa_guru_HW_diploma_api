# Проект по автоматизации тестовых сценариев для сайта [DemoQA (Книжный магазин)](https://demoqa.com/books)
<img width="500px" title="Wikipedia" src="media/logo/Bookstore.jpg">

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
<img width="6%" title="IntelliJ IDEA" src="media/logo/Intelij_IDEA.svg">
<img width="6%" title="Java" src="media/logo/Java.svg">
<img width="6%" title="Selenide" src="media/logo/Selenide.svg">
<img width="6%" title="Selenoid" src="media/logo/Selenoid.svg">
<img width="6%" title="Allure Report" src="media/logo/Allure_Report.svg">
<img width="5%" title="Allure TestOps" src="media/logo/AllureTestOps.svg">
<img width="6%" title="Gradle" src="media/logo/Gradle.svg">
<img width="6%" title="JUnit5" src="media/logo/JUnit5.svg">
<img width="6%" title="GitHub" src="media/logo/GitHub.svg">
<img width="6%" title="Jenkins" src="media/logo/Jenkins.svg">
<img width="6%" title="Telegram" src="media/logo/Telegram.svg">
</p>

Тесты в данном проекте написаны на языке <code>Java</code> с использованием фреймворка для тестирования [Selenide](https://selenide.org/), сборщик - <code>Gradle</code>. <code>JUnit 5</code> задействован в качестве фреймворка модульного тестирования. Тесты содержат API и UI тесты.
При прогоне тестов для запуска браузеров используется [Selenoid](https://selenoid.autotests.cloud).
Для удаленного запуска реализована джоба в <code>Jenkins</code> с формированием Allure-отчета и отправкой результатов в <code>Telegram</code> при помощи бота. Так же реализована интеграция с <code>Allure TestOps</code>.

**Особенности проекта**:
- `Page Object` шаблон проектирования
- Использование технологии `Owner` для придания тестам гибкости и легкости конфигурации
- Возможность запуска тестов: локально, удалённо, по тегам
- Использование `Faker` для генерации данных
- Использование `Lombok` для моделей в API тестах
- Возможность запуска тестов напрямую из Allure TestOps
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

Конфигурационные файлы `.config` лежат в папке `resources`. <br/>
При необходимости можно изменить их.

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

<p align="center">
<img title="Allure Overview" src="media/screens/allure.png">
</p>

## <img width="4%" style="vertical-align:middle" title="Allure TestOps" src="media/logo/AllureTestOps.svg"> Интеграция с <b><a target="_blank" href="https://allure.autotests.cloud/project/4964/dashboards">Allure TestOps</a></b>

На *Dashboard* в <code>Allure TestOps</code> видна статистика количества тестов: сколько из них добавлены и проходятся вручную, сколько автоматизированы. Новые тесты, а так же результаты прогона приходят по интеграции при каждом запуске сборки.

<p align="center">
<img title="Allure TestOps DashBoard" src="media/screens/testops.png">
</p>

### <b><a target="_blank" href="https://allure.autotests.cloud/launch/48835/tree/734119?search=W3siaWQiOiJzdGF0dXMiLCJ0eXBlIjoidGVzdFN0YXR1c0FycmF5IiwidmFsdWUiOlsicGFzc2VkIl19XQ%3D%3D&treeId=0">Результат выполнения автотестов</a></b>

<p align="center">
<img title="Test Results in Alure TestOps" src="media/screens/testopsResults.png">
</p>

### <img width="4%" style="vertical-align:middle" title="Telegram" src="media/logo/Telegram.svg"> Уведомления в Telegram с использованием бота

После завершения сборки специальный бот, созданный в <code>Telegram</code>, автоматически обрабатывает и отправляет сообщение с отчетом о прогоне тестов.

<p align="center">
<img width="70%" title="Telegram Notifications" src="media/screens/bot.jpg">
</p>

### <img width="4%" style="vertical-align:middle" title="Selenoid" src="media/logo/Selenoid.svg"> Видео примера запуска тестов в Selenoid

В отчетах Allure для каждого теста прикреплен не только скриншот, но и видео прохождения теста
<p align="center">
  <img title="Selenoid Video" src="media/video/tests.gif">
</p>