package io.github.recipestore.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime


@Table("RECIPE")
data class Recipe(
    @Id
    @Column("RECIPE_ID")
    var id: Long? = null,

    @Column("NAME")
    var name: String,

    @Column("DESCRIPTION")
    var description: String,

    @Column("DIFFICULTY")
    var difficulty: RecipeDifficulties,

    @Column("SERVINGS")
    var numServings: Int,

    @Column("SOURCE")
    var source: String,

    @Column("URL")
    var url: String,

    @JsonIgnore
    @Column("RECIPE_CATEGORY_ID")
    var categoryId: Long,

    @JsonIgnore
    @Column("USER_ID")
    var userId: Long,

    @Column("CREATED")
    var created: LocalDateTime = LocalDateTime.now(),

    @Column("MODIFIED")
    var modified: LocalDateTime = LocalDateTime.now(),
    ) {

    @Transient
    var user: User? = null

    @Transient
    var category: RecipeCategory? = null

    @Transient
    var steps: List<RecipeStep> = mutableListOf()
}