# recipestore
![Java CI with Gradle](https://github.com/AahzBrut/recipestore/workflows/Java%20CI%20with%20Gradle/badge.svg)

Kotlin Spring WebFlux reference project


# Usage

## Login (/api/v1/login)
Admin privileges:</br>
user name: admin</br>
password: 123</br>

User privileges:</br>
user name: user</br>
password: 123</br>

returns jwt token in response body, this token must be provided in auth header when calling other endpoints (see example Postman scripts).

## DB Console http://localhost:8082/
url: jdbc:h2:file:~/recipestore/db</br>
user: sa</br>
password:</br>
