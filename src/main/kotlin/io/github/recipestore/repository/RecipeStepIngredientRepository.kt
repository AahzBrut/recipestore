package io.github.recipestore.repository

import io.github.recipestore.domain.RecipeStepIngredient
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface RecipeStepIngredientRepository : ReactiveCrudRepository<RecipeStepIngredient, Long>