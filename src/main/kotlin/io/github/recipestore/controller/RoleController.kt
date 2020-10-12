package io.github.recipestore.controller

import io.github.recipestore.domain.Role
import io.github.recipestore.dto.request.RoleAddRequest
import io.github.recipestore.dto.request.RoleUpdateRequest
import io.github.recipestore.service.RoleService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
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


const val ROLES_PATH = "/api/v1/roles"
const val ROLES_BY_ID_PATH = "/api/v1/roles/{id}"

@RestController
class RoleController(
    private val roleService: RoleService
) {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(ROLES_PATH,
        produces = [APPLICATION_JSON_VALUE])
    fun getAllRoles(principal: Principal) : Flux<Role>{

        return roleService.getAllRoles()
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(ROLES_BY_ID_PATH,
        produces = [APPLICATION_JSON_VALUE])
    fun getRoleById(principal: Principal, @PathVariable id: Long) : Mono<Role> {

        return roleService.getRoleById(id)
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(ROLES_PATH,
        consumes = [APPLICATION_JSON_VALUE],
        produces = [APPLICATION_JSON_VALUE])
    fun addRole(principal: Principal, @RequestBody request: RoleAddRequest) : Mono<Role> {

        return roleService.addRole(request)
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(ROLES_PATH,
        consumes = [APPLICATION_JSON_VALUE],
        produces = [APPLICATION_JSON_VALUE])
    fun updateRole(principal: Principal, @RequestBody request: RoleUpdateRequest) : Mono<Role> {

        return roleService.updateRole(request)
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(ROLES_BY_ID_PATH)
    fun deleteRole(principal: Principal, @PathVariable id: Long) : Mono<Void> {

        return roleService.deleteRole(id)
    }
}