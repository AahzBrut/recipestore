package io.github.recipestore.service

import io.github.recipestore.domain.Role
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
}