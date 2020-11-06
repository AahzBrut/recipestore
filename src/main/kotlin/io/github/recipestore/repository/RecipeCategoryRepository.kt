package io.github.recipestore.repository

import io.github.recipestore.domain.RecipeCategory
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface RecipeCategoryRepository: ReactiveCrudRepository<RecipeCategory, Long> {

    fun findAllByParentCategoryId(parentId: Long?): Flux<RecipeCategory>

    @Modifying
    @Query("UPDATE RECIPE_CATEGORY SET NAME=:name, DESCRIPTION=:description, PARENT_CATEGORY_ID=:parentId, USER_ID=:userId, MODIFIED=CURRENT_TIMESTAMP() WHERE RECIPE_CATEGORY_ID=:id")
    fun updateCategory(id: Long, name: String, description: String, parentId: Long?, userId: Long): Mono<Int>
}