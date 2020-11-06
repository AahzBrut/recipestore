package io.github.recipestore.repository

import io.github.recipestore.domain.Ingredient
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface IngredientRepository : ReactiveCrudRepository<Ingredient, Long> {

    fun findAllByCategoryId(categoryId: Long): Flux<Ingredient>

    @Modifying
    @Query("UPDATE INGREDIENT SET NAME=:name, DESCRIPTION=:description, INGREDIENT_CATEGORY_ID=:categoryId, USER_ID=:userId, MODIFIED=CURRENT_TIMESTAMP() WHERE INGREDIENT_ID=:id")
    fun updateIngredient(id: Long, name: String, description: String, categoryId: Long, userId: Long): Mono<Int>

}