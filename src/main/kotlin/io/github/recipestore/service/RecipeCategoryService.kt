package io.github.recipestore.service

import io.github.recipestore.domain.RecipeCategory
import io.github.recipestore.dto.request.CategoryRequest
import io.github.recipestore.repository.RecipeCategoryRepository
import io.github.recipestore.repository.RecipeRepository
import io.github.recipestore.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Service
class RecipeCategoryService(
    private val repository: RecipeCategoryRepository,
    private val recipeRepository: RecipeRepository,
    private val userRepository: UserRepository
) {

    fun getAllCategories(): Flux<RecipeCategory> = getChildCategories(null)

    fun getChildCategories(parentId: Long?): Flux<RecipeCategory> = repository
        .findAllByParentCategoryId(parentId)
        .flatMap(::mapCategoryProperties)

    fun getCategory(id: Long): Mono<RecipeCategory> =
        repository.findById(id)
            .flatMap(::mapCategoryProperties)

    fun addCategory(userName: String, request: CategoryRequest): Mono<Void> =
        userRepository
            .findByName(userName)
            .flatMap { user ->
                repository
                    .save(
                        RecipeCategory(name = request.name, description = request.description, parentCategoryId = request.parentCategoryId, userId = user.id!!)
                    )
                Mono.empty()
            }

    fun updateCategory(id: Long, userName: String, request: CategoryRequest): Mono<Void> =
        userRepository
            .findByName(userName)
            .flatMap { user ->
                repository
                    .updateCategory(id, request.name, request.description, request.parentCategoryId, user.id!!)
                Mono.empty()
            }

    fun deleteCategory(id: Long): Mono<Void> = repository.deleteById(id)

    private fun mapCategoryProperties(category: RecipeCategory): Mono<RecipeCategory> =
        Mono.just(category)
            .zipWith(recipeRepository.findAllByCategoryId(category.id!!).collectList())
            .map { tuple ->
                tuple.t1.recipes = tuple.t2
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