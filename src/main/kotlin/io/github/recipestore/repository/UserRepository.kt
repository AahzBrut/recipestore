package io.github.recipestore.repository

import io.github.recipestore.domain.User
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface UserRepository: ReactiveCrudRepository<User, Long> {

    fun findByName(name: String): Mono<User>

    @Modifying
    @Query("UPDATE REPOSITORY.USER SET NAME=:name, PASSWORD=:password, MODIFIED=CURRENT_TIMESTAMP() WHERE USER_ID=:id")
    fun updateUser(id: Long, name: String, password: String): Mono<Int>
}