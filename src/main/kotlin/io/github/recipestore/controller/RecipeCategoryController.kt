package io.github.recipestore.controller

import io.github.recipestore.domain.RecipeCategory
import io.github.recipestore.dto.request.CategoryRequest
import io.github.recipestore.service.RecipeCategoryService
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


const val RECIPE_CATEGORIES_PATH = "/api/v1/recipes/categories"
const val RECIPE_CATEGORIES_BY_ID_PATH = "/api/v1/recipes/categories/{id}"

@RestController
class RecipeCategoryController(
    private val service: RecipeCategoryService
) {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(RECIPE_CATEGORIES_PATH)
    fun getAllRecipeCategory(principal: Principal): Flux<RecipeCategory> =
        service
            .getAllCategories()


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(RECIPE_CATEGORIES_PATH,
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun addRecipeCategory(principal: Principal, @RequestBody request: CategoryRequest): Mono<RecipeCategory> =
        service
            .addCategory(principal.name, request)

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(RECIPE_CATEGORIES_BY_ID_PATH,
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getRecipeCategory(principal: Principal, @PathVariable id: Long): Mono<RecipeCategory> =
        service
            .getCategory(id)

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping(RECIPE_CATEGORIES_BY_ID_PATH,
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateRecipeCategory(principal: Principal, @PathVariable id: Long, @RequestBody request: CategoryRequest): Mono<RecipeCategory> =
        service
            .updateCategory(id, principal.name, request)

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(RECIPE_CATEGORIES_BY_ID_PATH,
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteRecipeCategory(principal: Principal, @PathVariable id: Long): Mono<Void> =
        service
            .deleteCategory(id)
}