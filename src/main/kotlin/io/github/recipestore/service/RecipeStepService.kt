package io.github.recipestore.service

import io.github.recipestore.domain.RecipeStep
import io.github.recipestore.domain.RecipeStepIngredient
import io.github.recipestore.dto.request.RecipeStepAddRequest
import io.github.recipestore.repository.IngredientRepository
import io.github.recipestore.repository.RecipeStepIngredientRepository
import io.github.recipestore.repository.RecipeStepRepository
import io.github.recipestore.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class RecipeStepService(
    private val repository: RecipeStepRepository,
    private val userRepository: UserRepository,
    private val stepIngredientRepository: RecipeStepIngredientRepository,
    private val ingredientRepository: IngredientRepository
) {

    fun addRecipeStep(userName: String, recipeId: Long, request: RecipeStepAddRequest): Mono<Void> =
        userRepository
            .findByName(userName)
            .flatMap { user ->
                repository
                    .save(RecipeStep(null, request.name, request.description, request.ordinal, recipeId, user.id!!))
            }
            .flatMap {
                Mono.empty()
            }

    fun getRecipeStep(recipeStepId: Long): Mono<RecipeStep> =
        repository.findById(recipeStepId)
            .flatMap { step ->
                Mono.just(step)
                    .zipWith(getStepIngredients(step.id!!).collectList())
                    .map {
                        it.t1.ingredients = it.t2
                        ingredientRepository.findAllById(it.t1.ingredients.map(RecipeStepIngredient::ingredientId))
                        it.t1
                    }
            }

    fun deleteRecipeStep(recipeStepId: Long): Mono<Void> =
        repository.deleteById(recipeStepId)

    fun getAllRecipeSteps(recipeId: Long): Flux<RecipeStep> =
        repository
            .findAll()
            .flatMap { step ->
                Mono.just(step)
                    .zipWith(getStepIngredients(step.id!!).collectList())
                    .map {
                        it.t1.ingredients = it.t2
                        ingredientRepository.findAllById(it.t1.ingredients.map(RecipeStepIngredient::ingredientId))
                        it.t1
                    }
            }

    fun updateRecipeStep(userName: String, recipeStepId: Long, request: RecipeStepAddRequest): Mono<Void> =
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
                Mono.empty()
            }

    private fun getStepIngredients(stepId: Long): Flux<RecipeStepIngredient> =
        stepIngredientRepository
            .findAllByRecipeStepId(stepId)
            .flatMap { stepIngredient ->
                Mono.just(stepIngredient)
                    .zipWith(ingredientRepository.findById(stepIngredient.ingredientId))
                    .map {
                        it.t1.ingredient = it.t2
                        it.t1
                    }
            }
}
