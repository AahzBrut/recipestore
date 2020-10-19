package io.github.recipestore.repository

import io.github.recipestore.domain.Recipe
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface RecipeRepository : ReactiveCrudRepository<Recipe, Long> {

    fun findAllByCategoryId(categoryId: Long): Flux<Recipe>
}