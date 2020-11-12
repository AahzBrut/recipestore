package io.github.recipestore.mapper

import io.github.recipestore.domain.IngredientCategory
import io.github.recipestore.domain.User
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class IngredientCategoryAddUser : Merger<IngredientCategory, User> {

    override fun merge(target: Mono<IngredientCategory>, source: Mono<User>) = target
        .zipWith(source)
        .map {
            it.t1.user = it.t2
            it.t1
        }
}