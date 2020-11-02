package io.github.recipestore.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime


@Table("REPOSITORY.RECIPE_STEP_INGREDIENT")
data class RecipeStepIngredient(

    @Id
    @Column("RECIPE_STEP_INGREDIENT_ID")
    var id: Long? = null,

    @Column("NAME")
    var name: String,

    @Column("DESCRIPTION")
    var description: String,

    @JsonIgnore
    @Column("RECIPE_STEP_ID")
    var recipeStepId: Long,

    @JsonIgnore
    @Column("UOM_ID")
    var uomId: Long,

    @Column("UOM_AMOUNT")
    var uomAmount: Double,

    @JsonIgnore
    @Column("INGREDIENT_ID")
    var ingredientId: Long,

    @JsonIgnore
    @Column("USER_ID")
    var userId: Long,

    @Column("CREATED")
    var created: LocalDateTime = LocalDateTime.now(),

    @Column("MODIFIED")
    var modified: LocalDateTime = LocalDateTime.now(),
) {

    var uom: UOM? = null

    var ingredient: Ingredient? = null

    var user: User? = null
}