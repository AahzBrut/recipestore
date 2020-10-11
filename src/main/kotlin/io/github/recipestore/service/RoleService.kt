package io.github.recipestore.service

import io.github.recipestore.domain.Role
import io.github.recipestore.repository.RoleRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class RoleService(
    private val roleRepository: RoleRepository
) {

    fun getRolesById(roleId : Long ): Mono<Role> =
        roleRepository.findById(roleId)
}