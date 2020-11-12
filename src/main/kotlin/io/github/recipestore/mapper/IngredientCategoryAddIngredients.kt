package io.github.recipestore.mapper

import io.github.recipestore.domain.Ingredient
import io.github.recipestore.domain.IngredientCategory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class IngredientCategoryAddIngredients : Merger<IngredientCategory, Ingredient> {

    override fun merge(target: Mono<IngredientCategory>, source: Flux<Ingredient>) =
        target
            .zipWith(source.collectList())
            .map {
                it.t1.ingredients = it.t2
                it.t1
            }
}