# Учебный проект по курсу JAVA EE в  ОЦ "ПВТ".
> Приложение для сервиса по аренде автомобилей.

Какой функционал реализован на данный момент:
* Авторизация
* Регистрация пользователей
* Кабинет администратора
    * Админ видит список авто на главной странице. Может добавлять, удалять и редактировать автомобили.
    * У админа есть панель текущих заказов. Он может их или отклонить или принять для дальнейшей оплаты.
    * У админа есть панель Персонал. Админ может как создать работника, так и закрепить его за каким либо авто.
* Главная страница для "юзеров".
    * Юзер видит список доступных авто. Также авто которые доступны к заказу(не забронированы).
    * Юзер может сделать заказ, и после подтверждения админом, произвести оплату.

### Финальные требования к контрольной №1.
- [x] должен быть многомодульный проект, логика базы данных должна быть в dao модуле, логика работы с сетью в модуле веб
- [x] данные должны храниться в реляционной базе данных
- [x] Вся логика дао и сервис модуля должна быть доступна через интерфейсы
- [x] 30% кода дао и сервисов должны быть покрыто тестами
- [x] модуль сервисов должен быть протестирован с помощью моков
- [x] Для авторизации и аутентификации использовать Filter.
- [x] Классы и методы должны иметь отражающую их функциональность названия и должны быть грамотно структурированы по пакетам.
- [x] Приложение должно соответствовать шаблону Model-View-Controller или dto jsp servlet/service
- [x] Для получения объектов dao и service использовать шаблон Singleton.
- [x] Конфигурационную информацию хранить в properties-файле, например, такую как: параметры соединения с БД, граничные значения предметной области, локализацию
- [x] приложение должно запускаться на сервере приложений томкат
- [x] При разработке использовать журналирование событий (slf4j).
- [x] Используя сервлеты и JSP, реализовать функциональности, предложенные в постановке конкретной задачи.
- [x] В JSP-страницах использовать возможности библиотеки JSTL.
- [x] Приложение должно поддерживать работу с кириллицей, в том числе и при хранении информации в БД.
- [x] проект должен иметь функциональнось логина пользователя, добавления сущностей удаления и обновления и просмотр
- [x] все тесты должны быть положительны
- [x] не должно быть пустых методов
- [x] минимум 5 dao классов не пустых
- [x] минимум 10 сервлетов не пустых

### Финальные требования к контрольной №2.

- [x] Должны быть выполнены все требования к контрольной № 1(кроме jdbc)
- [x] Использовать контрольную работу №1 изменить модуль dao. Переписать его с использованием фреймворка Hibernate.
- [x] Использовать отношения между сущностями 1-1, 1-n, n-n.
- [x] Ограничить количество элементов выборки с помощью педжинации.
- [x] Для запросов использовать Hibernate Query и Criteria.
- [x] Использовать механизм пользовательских транзакций.
- [x] Подключить кэш второго уровня Hibernate.
- [x] 60% кода модулей dao и services покрыть unit-тестами.
- [x] должно быть минимум 5 таблиц и 5 сущности и 5 Dao и 10 сервлетов для работы с этими сущностями и связывающая их функциональность через сервисы
- [x] должен соблюдаться Java code convention.

### Финальные требования к итоговому проекту.
- [x] Должны быть выполнены все требования контрольной работу №2.
- [x] Для проекта произвести интеграцию Hibernate и Spring.
- [x] Использовать spring mvc для web, сервлетов не должно быть
- [x] В качестве представления данных использовать шаблоны фрейморка Tiles или аналог
- [x] Использовать модуль безопасности Spring для ограничения доступа
- [x] Реализовать интернационализацию сообщений через Spring.
- [x] Проект должен иметь законченную функциональность (все должно работать)
- [x] Должна быть полная функциональность (не просто 2 Dao и 2 service)
- [ ] Выполнение всех пунктов не гарантирует получение высшего балла
- [ ] Будет оцениваться: чистота кода, размеры классов и методов, имена переменных и соблюдение общих стандартов написание кода
- [ ] Будут задаваться теоретические вопросы при сдаче