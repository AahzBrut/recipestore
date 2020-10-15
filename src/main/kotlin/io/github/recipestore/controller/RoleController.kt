package io.github.recipestore.controller

import io.github.recipestore.domain.Role
import io.github.recipestore.service.RoleService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.security.Principal


const val ROLES_PATH = "/api/v1/roles"
const val ROLES_BY_ID_PATH = "/api/v1/roles/{id}"

@RestController
class RoleController(
    private val roleService: RoleService
) {

    private val logger by lazy { LoggerFactory.getLogger(RoleController::class.java) }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(ROLES_PATH,
        produces = [APPLICATION_JSON_VALUE])
    fun getAllRoles(principal: Principal): Flux<Role> = roleService
        .getAllRoles()
        .doOnSubscribe { logger.debug("User: ${principal.name} in getAllRoles()") }
        .doAfterTerminate { logger.debug("User: ${principal.name} out of getAllRoles()") }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(ROLES_BY_ID_PATH,
        produces = [APPLICATION_JSON_VALUE])
    fun getRoleById(principal: Principal, @PathVariable id: Long): Mono<Role> = roleService
        .getRoleById(id)
        .doOnSubscribe { logger.debug("User: ${principal.name} in getRoleById()") }
        .doAfterTerminate { logger.debug("User: ${principal.name} out of getRoleById()") }
}