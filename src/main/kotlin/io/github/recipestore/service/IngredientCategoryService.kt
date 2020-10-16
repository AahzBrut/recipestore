package io.github.recipestore.service

import io.github.recipestore.domain.IngredientCategory
import io.github.recipestore.dto.request.IngredientCategoryRequest
import io.github.recipestore.repository.IngredientCategoryRepository
import io.github.recipestore.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class IngredientCategoryService(
    private val repository: IngredientCategoryRepository,
    private val userRepository: UserRepository
) {

    fun getAllCategories(): Flux<IngredientCategory> = getChildCategories(null)

    fun getChildCategories(parentId: Long?): Flux<IngredientCategory> = repository
        .findAllByParentCategoryId(parentId)

    fun getCategory(id: Long): Mono<IngredientCategory> = repository.findById(id)

    fun addCategory(userName: String, request: IngredientCategoryRequest): Mono<IngredientCategory> =
        userRepository
            .findByName(userName)
            .flatMap { user ->
                repository
                    .save(
                        IngredientCategory(name = request.name, description = request.description, parentCategoryId = request.parentCategoryId, userId = user.id!!)
                    )
            }

    fun updateCategory(id: Long, userName: String, request: IngredientCategoryRequest): Mono<IngredientCategory> =
        userRepository
            .findByName(userName)
            .flatMap {user ->
                repository
                    .updateIngredientCategory(id, request.name, request.description, request.parentCategoryId, user.id!!)
            }
            .flatMap {
                getCategory(id)
            }

    fun deleteCategory(id: Long): Mono<Void> = repository.deleteById(id)
}
