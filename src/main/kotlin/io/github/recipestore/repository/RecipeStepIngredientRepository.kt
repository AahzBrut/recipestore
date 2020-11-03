package io.github.recipestore.repository

import io.github.recipestore.domain.RecipeStepIngredient
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface RecipeStepIngredientRepository : ReactiveCrudRepository<RecipeStepIngredient, Long> {

    fun findAllByRecipeStepId(recipeStepId: Long) : Flux<RecipeStepIngredient>
}