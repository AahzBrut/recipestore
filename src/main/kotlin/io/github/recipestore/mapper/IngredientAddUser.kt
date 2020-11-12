package io.github.recipestore.mapper

import io.github.recipestore.domain.Ingredient
import io.github.recipestore.domain.User
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class IngredientAddUser : Merger<Ingredient, User> {

    override fun merge(target: Mono<Ingredient>, source: Mono<User>) =
        target
            .zipWith(source)
            .map {
                it.t1.user = it.t2
                it.t1
            }
}