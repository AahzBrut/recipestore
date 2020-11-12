package io.github.recipestore.service

import io.github.recipestore.domain.IngredientCategory
import io.github.recipestore.dto.request.CategoryRequest
import io.github.recipestore.mapper.IngredientCategoryAddChildCategories
import io.github.recipestore.mapper.IngredientCategoryAddIngredients
import io.github.recipestore.mapper.IngredientCategoryAddUser
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
    private val ingredientRepository: IngredientRepository,
    private val ingredientCategoryAddIngredients: IngredientCategoryAddIngredients,
    private val ingredientCategoryAddChildCategories: IngredientCategoryAddChildCategories,
    private val ingredientCategoryAddUser: IngredientCategoryAddUser
) {

    fun getAllCategories(): Flux<IngredientCategory> = getChildCategories(null)

    fun getChildCategories(parentId: Long?): Flux<IngredientCategory> =
        repository.findAllByParentCategoryId(parentId)
            .flatMap { ingredientCategoryAddIngredients.merge(Mono.just(it), ingredientRepository.findAllByCategoryId(it.id!!)) }
            .flatMap { ingredientCategoryAddChildCategories.merge(Mono.just(it), repository.findAllByParentCategoryId(it.id!!)) }
            .flatMap { ingredientCategoryAddUser.merge(Mono.just(it), userRepository.findById(it.userId)) }

    fun getCategory(id: Long): Mono<IngredientCategory> =
        repository.findById(id)
            .flatMap { ingredientCategoryAddIngredients.merge(Mono.just(it), ingredientRepository.findAllByCategoryId(it.id!!)) }
            .flatMap { ingredientCategoryAddChildCategories.merge(Mono.just(it), repository.findAllByParentCategoryId(it.id!!)) }
            .flatMap { ingredientCategoryAddUser.merge(Mono.just(it), userRepository.findById(it.userId)) }

    fun addCategory(userName: String, request: CategoryRequest): Mono<IngredientCategory> =
        userRepository
            .findByName(userName)
            .flatMap { user ->
                repository
                    .save(IngredientCategory(name = request.name, description = request.description, parentCategoryId = request.parentCategoryId, userId = user.id!!))
            }
            .flatMap {
                getCategory(it.id!!)
            }

    fun updateCategory(id: Long, userName: String, request: CategoryRequest): Mono<IngredientCategory> =
        userRepository
            .findByName(userName)
            .flatMap { user ->
                repository
                    .updateCategory(id, request.name, request.description, request.parentCategoryId, user.id!!)
            }.flatMap {
                getCategory(id)
            }

    fun deleteCategory(id: Long): Mono<Void> = repository.deleteById(id)
}