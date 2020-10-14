package io.github.recipestore.controller

import io.github.recipestore.domain.User
import io.github.recipestore.dto.request.LoginRequest
import io.github.recipestore.dto.request.UserRolesAddRequest
import io.github.recipestore.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.UNAUTHORIZED
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.security.Principal


const val USER_LOGIN_PATH = "/api/v1/login"
const val USER_SIGNON_PATH = "/api/v1/sign-on"
const val USERS_PATH = "/api/v1/users"
const val USER_ROLES_PATH = "/api/v1/users/{id}/roles"


@RestController
class UserController(
    private val userService: UserService
) {

    @PostMapping(USER_LOGIN_PATH)
    fun login(@RequestBody request: LoginRequest): Mono<ResponseEntity<String>> {

        return userService
            .login(request)
            .map { ResponseEntity.ok(it) }
            .defaultIfEmpty(ResponseEntity.status(UNAUTHORIZED).build())
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(USERS_PATH,
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllUsers(principal: Principal) : Flux<User> {

        return userService.getAllUsers()
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(USER_SIGNON_PATH,
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun addUser(principal: Principal, @RequestBody request: LoginRequest) : Mono<User> =
        userService
            .addUser(request)

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(USER_ROLES_PATH,
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun addUserRoles(principal: Principal, @PathVariable id: Long, @RequestBody request: UserRolesAddRequest) : Mono<User> =
        userService
            .addUserRoles(id, request)
}