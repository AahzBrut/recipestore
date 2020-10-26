package io.github.recipestore.repository

import io.github.recipestore.domain.Recipe
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface RecipeRepository : ReactiveCrudRepository<Recipe, Long> {

    fun findAllByCategoryId(categoryId: Long): Flux<Recipe>

    @Modifying
    @Query("UPDATE REPOSITORY.RECIPE SET " +
        "NAME=:name, " +
        "DESCRIPTION=:description, " +
        "DIFFICULTY=:difficulty, " +
        "SERVINGS=:servings, " +
        "SOURCE=:source, " +
        "URL=:url, " +
        "RECIPE_CATEGORY_ID=:categoryId, " +
        "USER_ID=:userId, " +
        "MODIFIED=CURRENT_TIMESTAMP() WHERE RECIPE_ID=:id")
    fun updateRecipe(id: Long, name: String, description: String, difficulty: String, servings: Int, source: String, url: String, categoryId: Long, userId: Long): Mono<Int>
}