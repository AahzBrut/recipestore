package io.github.recipestore.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("RECIPE_STEP")
data class RecipeStep(

    @Id
    @Column("RECIPE_STEP_ID")
    var id: Long? = null,

    @Column("NAME")
    var name: String,

    @Column("DESCRIPTION")
    var description: String,

    @Column("ORDINAL")
    var ordinal: Int,

    @JsonIgnore
    @Column("RECIPE_ID")
    var recipeId: Long,

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
    var ingredients: List<RecipeStepIngredient> = mutableListOf()
}