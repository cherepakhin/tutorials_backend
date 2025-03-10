## Backend на kotlin и Spring Boot 

frontend: [https://github.com/cherepakhin/tutorial_frontend](https://github.com/cherepakhin/tutorial_frontend)

### Тесты:

````shell
$ ./gradlew test
````
### Покрытие тестами (test coverage)

После прогона тестов, результаты будут в `build/reports/jacoco/test/html/index.html`

### Запуск:

````shell
$ ./run_project.sh
````

Простая проверка сервиса:

````shell
$ http :8980/api/tutorial/echo/aaa
````

