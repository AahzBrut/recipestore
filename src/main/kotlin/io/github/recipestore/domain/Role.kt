package io.github.recipestore.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("REPOSITORY.ROLE")
data class Role(
    @Id
    @Column("ROLE_ID")
    var id: Long?,

    @Column("NAME")
    var name: String,

    @Column("DESCRIPTION")
    var description: String
)