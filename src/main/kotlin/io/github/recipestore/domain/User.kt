package io.github.recipestore.domain

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("REPOSITORY.USER")
data class User(
    @Id
    @Column("USER_ID")
    var id: Long?,

    @Column("NAME")
    var name: String,

    @Column("PASSWORD")
    var password: String,

    @Transient
    var roles: Set<Role> = emptySet()
)