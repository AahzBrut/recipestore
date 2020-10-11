package io.github.recipestore.service

import io.github.recipestore.domain.Role
import io.github.recipestore.repository.UserRoleRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class UserRoleService(
    private val userRoleRepository: UserRoleRepository,
    private val roleService: RoleService
) {

    fun getUserRoles(userId: Long) : Flux<Role> =
        userRoleRepository
            .findAllByUserId(userId)
            .flatMap { it.roleId?.let { roleId -> roleService.getRolesById(roleId) } }

}