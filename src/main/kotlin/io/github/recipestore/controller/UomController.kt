package io.github.recipestore.controller

import io.github.recipestore.domain.UOM
import io.github.recipestore.service.UomService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.security.Principal

const val UOM_PATH = "/api/v1/uoms"
const val UOM_BY_ID_PATH = "/api/v1/uoms/{id}"

@RestController
class UomController(
    private val service: UomService
) {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(UOM_PATH,
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllRoles(principal: Principal): Flux<UOM> = service
        .getAllUOMs()

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(UOM_BY_ID_PATH,
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getRoleById(principal: Principal, @PathVariable id: Long): Mono<UOM> = service
        .getUOMById(id)
}