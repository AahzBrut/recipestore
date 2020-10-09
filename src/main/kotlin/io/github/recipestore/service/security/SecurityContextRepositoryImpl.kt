package io.github.recipestore.service.security

import io.github.recipestore.util.TOKEN_PREFIX
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Service
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@Service
class SecurityContextRepositoryImpl(
        private val authenticationManager: ReactiveAuthenticationManager
) : ServerSecurityContextRepository {

    override fun save(exchange: ServerWebExchange, context: SecurityContext): Mono<Void> {
        throw UnsupportedOperationException("Save method not supported")
    }

    override fun load(exchange: ServerWebExchange): Mono<SecurityContext> {

        val authHeader = exchange.request
                .headers
                .getFirst(HttpHeaders.AUTHORIZATION)

        return if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) {
            val authToken = authHeader.substring(TOKEN_PREFIX.length)
            val auth = UsernamePasswordAuthenticationToken(authToken, authToken)

            authenticationManager
                    .authenticate(auth)
                    .map { authentication: Authentication? -> SecurityContextImpl(authentication) }
        } else {
            Mono.empty()
        }
    }
}