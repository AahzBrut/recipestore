package io.github.recipestore.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("ROLE")
data class Role(
    @Id
    var id: Long?,

    var name: String,

    var description: String
)