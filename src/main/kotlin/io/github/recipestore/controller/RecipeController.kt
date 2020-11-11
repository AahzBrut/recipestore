package io.github.recipestore.controller

import io.github.recipestore.domain.Recipe
import io.github.recipestore.dto.request.RecipeRequest
import io.github.recipestore.service.RecipeService
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


const val RECIPES_PATH = "/api/v1/recipes"
const val RECIPES_BY_ID_PATH = "/api/v1/recipes/{id}"


@RestController
class RecipeController(
    private val service: RecipeService
) {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(RECIPES_PATH,
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun addRecipe(principal: Principal, @RequestBody request: RecipeRequest): Mono<Recipe> =
        service.addRecipe(principal.name, request)

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(RECIPES_BY_ID_PATH,
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getRecipe(principal: Principal, @PathVariable id: Long): Mono<Recipe> =
        service.getRecipe(id)

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(RECIPES_PATH,
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllRecipes(principal: Principal): Flux<Recipe> =
        service.getAllRecipes()

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(RECIPES_BY_ID_PATH,
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateRecipe(principal: Principal, @PathVariable id: Long, @RequestBody request: RecipeRequest): Mono<Recipe> =
        service.updateRecipe(id, principal.name, request)

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(RECIPES_BY_ID_PATH,
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteRecipe(principal: Principal, @PathVariable id: Long): Mono<Void> =
        service.deleteRecipe(id)
}