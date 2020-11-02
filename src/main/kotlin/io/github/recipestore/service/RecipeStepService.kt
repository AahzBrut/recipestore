package io.github.recipestore.service

import io.github.recipestore.domain.RecipeStep
import io.github.recipestore.domain.RecipeStepIngredient
import io.github.recipestore.dto.request.RecipeRequest
import io.github.recipestore.dto.request.RecipeStepAddRequest
import io.github.recipestore.repository.RecipeStepIngredientRepository
import io.github.recipestore.repository.RecipeStepRepository
import io.github.recipestore.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.security.Principal

@Service
class RecipeStepService(
    private val repository: RecipeStepRepository,
    private val userRepository: UserRepository,
    private val stepIngredientRepository: RecipeStepIngredientRepository
) {

    fun addRecipeStep(userName: String, recipeId: Long, request: RecipeStepAddRequest): Mono<RecipeStep> =
        userRepository
            .findByName(userName)
            .flatMap { user ->
                repository
                    .save(RecipeStep(null, request.name, request.description, request.ordinal, recipeId, user.id!!))
                    .flatMap { recipeStep ->
                        Mono
                            .just(recipeStep)
                            .zipWith(stepIngredientRepository.saveAll(request.ingredients.map {
                                RecipeStepIngredient(null, it.name, it.description, recipeStep.id!!, it.uomId, it.uomAmount, it.ingredientId, user.id!!)
                            }).collectList())
                            .map {
                                it.t1.ingredients = it.t2
                                it.t1.user = user
                                it.t1
                            }
                    }

            }

    fun getRecipeStep(recipeStepId: Long): Mono<RecipeStep> =
        repository.findById(recipeStepId)

    fun deleteRecipeStep(recipeStepId: Long): Mono<Void> =
        repository.deleteById(recipeStepId)

    fun getAllRecipeSteps(recipeId: Long): Flux<RecipeStep> =
        repository.findAll()

    fun updateRecipeStep(userName: String, recipeStepId: Long, request: RecipeStepAddRequest): Mono<RecipeStep> =
        userRepository
            .findByName(userName)
            .flatMap {
                repository
                    .updateRecipeStep(recipeStepId,
                        request.name,
                        request.description,
                        request.ordinal,
                        it.id!!)
            }
            .flatMap {
                getRecipeStep(recipeStepId)
            }


}
