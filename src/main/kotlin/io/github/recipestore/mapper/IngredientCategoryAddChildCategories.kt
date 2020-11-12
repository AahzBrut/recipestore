package io.github.recipestore.mapper

import io.github.recipestore.domain.IngredientCategory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class IngredientCategoryAddChildCategories : Merger<IngredientCategory, IngredientCategory> {

    override fun merge(target: Mono<IngredientCategory>, source: Flux<IngredientCategory>) =
        target
            .zipWith(source.collectList())
            .map {
                it.t1.childCategories = it.t2
                it.t1
            }
}