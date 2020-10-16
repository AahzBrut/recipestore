package io.github.recipestore.repository

import io.github.recipestore.domain.IngredientCategory
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface IngredientCategoryRepository : ReactiveCrudRepository<IngredientCategory, Long> {

    fun findAllByParentCategoryId(parentId: Long?): Flux<IngredientCategory>

    @Modifying
    @Query("UPDATE REPOSITORY.INGREDIENT_CATEGORY SET NAME=:name, DESCRIPTION=:description, PARENT_CATEGORY_ID=:parentId, USER_ID=:userId, MODIFIED=CURRENT_TIMESTAMP() WHERE INGREDIENT_CATEGORY_ID=:id")
    fun updateIngredientCategory(id: Long, name: String, description: String, parentId: Long?, userId: Long): Mono<Int>
}