package io.github.recipestore.service

import io.github.recipestore.domain.User
import io.github.recipestore.dto.request.LoginRequest
import io.github.recipestore.repository.UserRepository
import io.github.recipestore.service.security.JwtService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class UserService(
    private val encoder: PasswordEncoder,
    private val userRepository: UserRepository,
    private val jwtService: JwtService
) {

    fun login(request: LoginRequest): Mono<String> {

        val user = User(null, request.userName, request.password)

        return userRepository
            .save(user)
            .map { it.name }

    }
}