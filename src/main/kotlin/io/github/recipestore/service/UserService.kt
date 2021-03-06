package io.github.recipestore.service

import io.github.recipestore.domain.Roles
import io.github.recipestore.domain.User
import io.github.recipestore.dto.request.LoginRequest
import io.github.recipestore.dto.request.UserRolesAddRequest
import io.github.recipestore.mapper.UserAddRoles
import io.github.recipestore.repository.UserRepository
import io.github.recipestore.service.security.JwtService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class UserService(
    private val encoder: PasswordEncoder,
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val userRoleService: UserRoleService,
    private val userAddRoles: UserAddRoles
) {

    fun login(request: LoginRequest): Mono<String> = userRepository
        .findByName(request.userName)
        .filter { encoder.matches(request.password, it.password) }
        .flatMap { user ->
            Mono.just(user)
                .zipWith(userRoleService.getUserRoles(user.id!!).collectList())
                .map { tuple ->
                    tuple.t1.roles = tuple.t2.toSet()
                    jwtService.generateToken(tuple.t1)
                }
        }

    fun getAllUsers(): Flux<User> = userRepository
        .findAll()
        .flatMap {
            userAddRoles.merge(Mono.just(it), userRoleService.getUserRoles(it.id!!))
        }

    fun getUser(userId: Long): Mono<User> = userRepository
        .findById(userId)
        .flatMap {
            userAddRoles.merge(Mono.just(it), userRoleService.getUserRoles(it.id!!))
        }

    fun addUser(request: LoginRequest): Mono<Void> = Mono.just(request)
        .map { User(null, it.userName, encoder.encode(it.password)) }
        .flatMap(userRepository::save)
        .map {
            it.also {
                userRoleService.addRolesToUser(it, setOf(Roles.USER))
            }
        }
        .flatMap { Mono.empty() }

    fun addUserRoles(userId: Long, request: UserRolesAddRequest): Mono<User> = Mono.just(request)
        .flatMap {
            userRoleService.addRolesToUser(User(userId, "", ""), it.roles)
            getUser(userId)
        }

    fun updateUser(userId: Long, request: LoginRequest): Mono<User> = userRepository.findById(userId)
        .flatMap {
            userRepository.updateUser(userId, request.userName, encoder.encode(request.password))
        }
        .flatMap {
            getUser(userId)
        }
}