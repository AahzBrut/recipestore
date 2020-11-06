package io.github.recipestore.repository

import io.github.recipestore.domain.RecipeStep
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface RecipeStepRepository: ReactiveCrudRepository<RecipeStep, Long> {

    @Modifying
    @Query("UPDATE RECIPE_STEP SET " +
        "NAME=:name, " +
        "DESCRIPTION=:description, " +
        "ORDINAL=:ordinal, " +
        "USER_ID=:userId, " +
        "MODIFIED=CURRENT_TIMESTAMP() WHERE RECIPE_STEP_ID=:id")
    fun updateRecipeStep(
        id: Long,
        name: String,
        description: String,
        ordinal: Int,
        userId: Long): Mono<Int>
}