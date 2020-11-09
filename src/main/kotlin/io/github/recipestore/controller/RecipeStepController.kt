package io.github.recipestore.controller

import io.github.recipestore.domain.RecipeStep
import io.github.recipestore.dto.request.RecipeRequest
import io.github.recipestore.dto.request.RecipeStepAddRequest
import io.github.recipestore.service.RecipeStepService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.security.Principal

const val RECIPE_STEPS_PATH = "/api/v1/recipes/{recipeId}/steps"
const val RECIPE_STEP_BY_ID_PATH = "/api/v1/recipes/steps/{id}"

@RestController
class RecipeStepController(
    private val service: RecipeStepService
) {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(RECIPE_STEPS_PATH,
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun addRecipeStep(principal: Principal, @PathVariable recipeId: Long, @RequestBody request: RecipeStepAddRequest): Mono<Void> =
        service.addRecipeStep(principal.name, recipeId, request)

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(RECIPE_STEP_BY_ID_PATH,
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getRecipeStep(principal: Principal, @PathVariable id: Long): Mono<RecipeStep> =
        service.getRecipeStep(id)

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(RECIPE_STEPS_PATH,
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllRecipeSteps(principal: Principal, @PathVariable recipeId: Long): Flux<RecipeStep> =
        service.getAllRecipeSteps(recipeId)

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(RECIPE_STEP_BY_ID_PATH,
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateRecipe(principal: Principal, @PathVariable id: Long, @RequestBody request: RecipeStepAddRequest): Mono<Void> =
        service.updateRecipeStep(principal.name, id, request)

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(RECIPE_STEP_BY_ID_PATH,
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteRecipe(principal: Principal, @PathVariable id: Long): Mono<Void> =
        service.deleteRecipeStep(id)
}