package io.github.recipestore.dto.response

data class UserResponse(
        val id: Long,
        val userName: String,
        val roles: List<RoleResponse>
)