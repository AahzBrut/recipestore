package io.github.recipestore.service

import io.github.recipestore.domain.Role
import io.github.recipestore.dto.request.RoleAddRequest
import io.github.recipestore.dto.request.RoleUpdateRequest
import io.github.recipestore.repository.RoleRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class RoleService(
    private val roleRepository: RoleRepository
) {

    fun getRoleById(roleId: Long): Mono<Role> =
        roleRepository.findById(roleId)

    fun getAllRoles(): Flux<Role> =
        roleRepository.findAll()

    fun addRole(request: RoleAddRequest): Mono<Role> = Mono
        .just(request)
        .map { Role(null, it.name, it.description) }
        .flatMap { roleRepository.save(it) }

    fun updateRole(request: RoleUpdateRequest): Mono<Role> = Mono
        .just(request)
        .map { Role(request.id, request.name, request.description) }
        .flatMap { roleRepository.save(it) }

    fun deleteRole(id: Long): Mono<Void> = roleRepository.deleteById(id)
}