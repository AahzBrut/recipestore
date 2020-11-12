package io.github.recipestore.mapper

import io.github.recipestore.domain.Role
import io.github.recipestore.domain.User
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class UserAddRoles : Merger<User, Role> {

    override fun merge(target: Mono<User>, source: Flux<Role>): Mono<User> =
        target
            .zipWith(source.collectList())
            .map {
                it.t1.roles = it.t2.toSet()
                it.t1
            }
}