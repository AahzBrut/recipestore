package io.github.recipestore.controller

import io.github.recipestore.domain.Ingredient
import io.github.recipestore.dto.request.IngredientRequest
import io.github.recipestore.service.IngredientService
import org.springframework.core.io.Resource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.security.Principal


const val INGREDIENTS_PATH = "/api/v1/ingredients"
const val INGREDIENTS_BY_ID_PATH = "/api/v1/ingredients/{id}"
const val INGREDIENTS_IMAGE_PATH = "/api/v1/ingredients/{id}/image"

@RestController
class IngredientController(
    private val service: IngredientService
) {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(INGREDIENTS_PATH,
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun addIngredient(principal: Principal, @RequestBody request: IngredientRequest): Mono<Ingredient> =
        service.addIngredient(principal.name, request)

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(INGREDIENTS_BY_ID_PATH,
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getIngredient(principal: Principal, @PathVariable id: Long): Mono<Ingredient> =
        service.getIngredient(id)

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(INGREDIENTS_PATH,
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllIngredients(principal: Principal): Flux<Ingredient> =
        service.getAllIngredients()

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(INGREDIENTS_BY_ID_PATH,
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateIngredient(principal: Principal, @PathVariable id: Long, @RequestBody request: IngredientRequest): Mono<Ingredient> =
        service.updateIngredient(principal.name, id, request)

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(INGREDIENTS_BY_ID_PATH,
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteIngredient(principal: Principal, @PathVariable id: Long): Mono<Void> =
        service
            .deleteIngredient(id)

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(INGREDIENTS_IMAGE_PATH,
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun setImage(principal: Principal, @PathVariable id: Long, @RequestPart("image") image: Mono<FilePart>): Mono<Void> =
        service.setImage(id, image)

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(INGREDIENTS_IMAGE_PATH,
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun getImage(principal: Principal, @PathVariable id: Long): Mono<Resource> =
        service.getImage(id)
}