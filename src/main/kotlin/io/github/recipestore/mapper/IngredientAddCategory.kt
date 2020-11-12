package io.github.recipestore.mapper

import io.github.recipestore.domain.Ingredient
import io.github.recipestore.domain.IngredientCategory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class IngredientAddCategory : Merger<Ingredient, IngredientCategory> {

    override fun merge(target: Mono<Ingredient>, source: Mono<IngredientCategory>) =
        target
            .zipWith(source)
            .map {
                it.t1.category = it.t2
                it.t1
            }
}