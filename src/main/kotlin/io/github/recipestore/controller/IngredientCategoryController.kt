package io.github.recipestore.controller

import io.github.recipestore.domain.IngredientCategory
import io.github.recipestore.dto.request.CategoryRequest
import io.github.recipestore.service.IngredientCategoryService
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


const val INGREDIENTS_CATEGORIES_PATH = "/api/v1/ingredients/categories"
const val INGREDIENTS_CATEGORIES_BY_ID_PATH = "/api/v1/ingredients/categories/{id}"


@RestController
class IngredientCategoryController(
    private val service: IngredientCategoryService
) {


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(INGREDIENTS_CATEGORIES_PATH)
    fun getAllIngredientCategory(principal: Principal): Flux<IngredientCategory> =
        service
            .getAllCategories()


    @ResponseStatus(HttpStatus.OK)
    @PostMapping(INGREDIENTS_CATEGORIES_PATH,
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun addIngredientCategory(principal: Principal, @RequestBody request: CategoryRequest): Mono<Void> =
        service
            .addCategory(principal.name, request)

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(INGREDIENTS_CATEGORIES_BY_ID_PATH,
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getIngredientCategory(principal: Principal, @PathVariable id: Long): Mono<IngredientCategory> =
        service
            .getCategory(id)

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(INGREDIENTS_CATEGORIES_BY_ID_PATH,
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateIngredientCategory(principal: Principal, @PathVariable id: Long, @RequestBody request: CategoryRequest): Mono<Void> =
        service
            .updateCategory(id, principal.name, request)

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(INGREDIENTS_CATEGORIES_BY_ID_PATH,
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteIngredientCategory(principal: Principal, @PathVariable id: Long): Mono<Void> =
        service
            .deleteCategory(id)
}