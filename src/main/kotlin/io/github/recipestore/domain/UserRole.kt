package io.github.recipestore.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("USER_ROLE")
data class UserRole(
    @Id
    var id: Long?,

    var userId: Long?,

    var roleId: Long?
)