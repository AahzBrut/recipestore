package io.github.recipestore.service

import io.github.recipestore.domain.Role
import io.github.recipestore.domain.Roles
import io.github.recipestore.domain.User
import io.github.recipestore.domain.UserRole
import io.github.recipestore.repository.UserRoleRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class UserRoleService(
    private val userRoleRepository: UserRoleRepository,
    private val roleService: RoleService
) {

    fun getUserRoles(userId: Long): Flux<Role> =
        userRoleRepository
            .findAllByUserId(userId)
            .flatMap { userRole -> roleService.getRoleById(userRole.roleId) }

    fun addRolesToUser(user: User, roles: Set<Roles>) {

        user.id?.let { userId ->
            userRoleRepository.deleteAllByUserId(userId)
            roles.forEach {
                val userRole = UserRole(null, userId, it.ordinal.toLong())
                userRoleRepository.save(userRole).subscribe() }
        }
    }

}