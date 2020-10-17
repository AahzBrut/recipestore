package io.github.recipestore.service

import io.github.recipestore.domain.IngredientCategory
import io.github.recipestore.dto.request.IngredientCategoryRequest
import io.github.recipestore.repository.IngredientCategoryRepository
import io.github.recipestore.repository.IngredientRepository
import io.github.recipestore.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class IngredientCategoryService(
    private val repository: IngredientCategoryRepository,
    private val userRepository: UserRepository,
    private val ingredientRepository: IngredientRepository
) {

    fun getAllCategories(): Flux<IngredientCategory> = getChildCategories(null)

    fun getChildCategories(parentId: Long?): Flux<IngredientCategory> = repository
        .findAllByParentCategoryId(parentId)
        .flatMap(::mapCategoryProperties)

    fun getCategory(id: Long): Mono<IngredientCategory> =
        repository.findById(id)
            .flatMap(::mapCategoryProperties)

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
            .flatMap { user ->
                repository
                    .updateIngredientCategory(id, request.name, request.description, request.parentCategoryId, user.id!!)
            }
            .flatMap {
                getCategory(id)
            }

    fun deleteCategory(id: Long): Mono<Void> = repository.deleteById(id)

    private fun mapCategoryProperties(category: IngredientCategory): Mono<IngredientCategory> =
        Mono.just(category)
            .zipWith(ingredientRepository.findAllByCategoryId(category.id!!).collectList())
            .map { tuple ->
                tuple.t1.ingredients = tuple.t2
                tuple.t1
            }
            .zipWith(repository.findAllByParentCategoryId(category.id!!).collectList())
            .map { tuple ->
                tuple.t1.childCategories = tuple.t2
                tuple.t1
            }
            .zipWith(userRepository.findById(category.userId))
            .map { tuple ->
                tuple.t1.user = tuple.t2
                tuple.t1
            }
}
