package io.github.recipestore.config

import io.github.recipestore.controller.USER_LOGIN_PATH
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class WebSecurityConfig(
        private val authenticationManager: ReactiveAuthenticationManager,
        private val securityContextRepository: ServerSecurityContextRepository
) {

    @Bean
    fun securityWebFilterChain(httpSecurity: ServerHttpSecurity): SecurityWebFilterChain {
        return httpSecurity
                .exceptionHandling()
                .authenticationEntryPoint { swe: ServerWebExchange, _: AuthenticationException? ->
                    Mono.fromRunnable { swe.response.statusCode = HttpStatus.UNAUTHORIZED }
                }
                .accessDeniedHandler { swe: ServerWebExchange, _: AccessDeniedException? ->
                    Mono.fromRunnable { swe.response.statusCode = HttpStatus.FORBIDDEN }
                }
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
                .pathMatchers("/", USER_LOGIN_PATH, "/favicon.ico", "/h2-console").permitAll()
                .pathMatchers("/controller").hasRole("ADMIN")
                .anyExchange().authenticated()
                .and()
                .build()
    }
}