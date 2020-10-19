package io.github.recipestore.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table


@Table("REPOSITORY.RECIPE")
data class Recipe(
    @Id
    @Column("RECIPE_ID")
    var id: Long?,

    @Column("NAME")
    var name: String,

    @Column("DESCRIPTION")
    var description: String,

    @Column("RECIPE_CATEGORY_ID")
    var categoryId: Long,

    @Column("USER_ID")
    var userId: Long
)