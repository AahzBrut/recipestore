package io.github.recipestore.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime


@Table("RECIPE_CATEGORY")
data class RecipeCategory(
    @Id
    @Column("RECIPE_CATEGORY_ID")
    var id: Long? = null,

    @Column("NAME")
    var name: String,

    @Column("DESCRIPTION")
    var description: String,

    @JsonIgnore
    @Column("PARENT_CATEGORY_ID")
    var parentCategoryId: Long? = null,

    @JsonIgnore
    @Column("USER_ID")
    var userId: Long,

    @Column("CREATED")
    var created: LocalDateTime = LocalDateTime.now(),

    @Column("MODIFIED")
    var modified: LocalDateTime = LocalDateTime.now()
    ) {

    @Transient
    var childCategories: List<RecipeCategory> = mutableListOf()

    @Transient
    var recipes: List<Recipe> = mutableListOf()

    @Transient
    var user: User? = null
}