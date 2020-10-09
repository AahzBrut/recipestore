package io.github.recipestore.service.security

import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


@Service
class AuthenticationManagerImpl(
        private val jwtService: JwtService
): ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication): Mono<Authentication> {

        val authToken = authentication.credentials.toString()

        val userName: String? = jwtService.extractUsername(authToken)

        return if (userName!=null && jwtService.validateToken(authToken)) {
            val authenticationToken = UsernamePasswordAuthenticationToken(
                    userName,
                    null,
                    jwtService.getAuthoritiesFromToken(authToken))

            Mono.just(authenticationToken)
        } else {
            Mono.empty()
        }
    }
}