package io.github.recipestore.service

import io.github.recipestore.domain.Recipe
import io.github.recipestore.dto.request.RecipeRequest
import io.github.recipestore.repository.RecipeRepository
import io.github.recipestore.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Service
class RecipeService(
    private val repository: RecipeRepository,
    private val userRepository: UserRepository
) {

    fun getAllRecipes(): Flux<Recipe> =
        repository.findAll()

    fun getRecipe(id: Long): Mono<Recipe> =
        repository.findById(id)

    fun addRecipe(userName: String, request: RecipeRequest): Mono<Recipe> =
        userRepository
            .findByName(userName)
            .flatMap { user ->
                repository.save(
                    Recipe(
                        name = request.name,
                        description = request.description,
                        difficulty = request.difficulty,
                        numServings = request.servings,
                        source = request.source,
                        url = request.url,
                        categoryId = request.categoryId,
                        userId = user.id!!
                    )
                )
            }


    fun updateRecipe(id: Long, userName: String, request: RecipeRequest): Mono<Recipe> =
        userRepository
            .findByName(userName)
            .flatMap {
                repository
                    .updateRecipe(id,
                        request.name,
                        request.description,
                        request.difficulty.name,
                        request.servings,
                        request.source,
                        request.url,
                        request.categoryId,
                        it.id!!)
            }
            .flatMap {
                getRecipe(id)
            }

    fun deleteRecipe(id: Long): Mono<Void> =
        repository.deleteById(id)
}
