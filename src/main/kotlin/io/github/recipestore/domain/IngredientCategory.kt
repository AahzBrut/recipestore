package io.github.recipestore.domain

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("REPOSITORY.INGREDIENT_CATEGORY")
data class IngredientCategory(
    @Id
    @Column("INGREDIENT_CATEGORY_ID")
    var id: Long? = null,

    @Column("NAME")
    var name: String,

    @Column("DESCRIPTION")
    var description: String,

    @Column("PARENT_CATEGORY_ID")
    var parentCategoryId: Long? = null,

    @Column("USER_ID")
    var userId: Long,

    @Column("CREATED")
    var created: LocalDateTime = LocalDateTime.now(),

    @Column("MODIFIED")
    var modified: LocalDateTime = LocalDateTime.now(),

    @Transient
    var childCategories: List<IngredientCategory> = mutableListOf()
    )