package io.github.recipestore.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("REPOSITORY.USER_ROLE")
data class UserRole(
    @Id
    @Column("USER_ROLE_ID")
    var id: Long?,

    @Column("USER_ID")
    var userId: Long,

    @Column("ROLE_ID")
    var roleId: Long
)