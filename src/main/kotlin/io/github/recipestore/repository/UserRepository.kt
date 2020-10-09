package io.github.recipestore.repository

import io.github.recipestore.domain.User
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface UserRepository: ReactiveCrudRepository<User, Long> {

    fun findByName(name: String): Mono<User>
}