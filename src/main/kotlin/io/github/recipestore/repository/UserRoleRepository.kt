package io.github.recipestore.repository

import io.github.recipestore.domain.UserRole
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserRoleRepository : ReactiveCrudRepository<UserRole, Long> {

    fun findAllByUserId(userId: Long): Flux<UserRole>

    fun deleteAllByUserId(userId: Long): Mono<Void>
}