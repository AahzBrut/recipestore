package io.github.recipestore.service

import io.github.recipestore.dto.request.LoginRequest
import io.github.recipestore.repository.UserRepository
import io.github.recipestore.service.security.JwtService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserService(
    private val encoder: PasswordEncoder,
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val userRoleService: UserRoleService
) {

    fun login(request: LoginRequest): Mono<String> = userRepository
        .findByName(request.userName)
        .filter { encoder.matches(request.password, it.password) }
        .flatMap { user ->
            Mono.just(user)
                .zipWith(userRoleService.getUserRoles(user.id!!).collectList())
                .map {
                    it.t1.roles = it.t2.toSet()
                    jwtService.generateToken(it.t1)
                }
        }
}