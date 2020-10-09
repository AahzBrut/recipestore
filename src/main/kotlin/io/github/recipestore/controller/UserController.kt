package io.github.recipestore.controller

import io.github.recipestore.dto.request.LoginRequest
import io.github.recipestore.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


const val USER_LOGIN_PATH = "/api/v1/login"

@RestController
class UserController(
        private val userService: UserService
) {

    @PostMapping(USER_LOGIN_PATH)
    fun login(@RequestBody request: LoginRequest): Mono<ResponseEntity<Any>> {

        return Mono.empty()
    }
}