package io.github.recipestore.dto.request

data class RecipeStepIngredientAddRequest (

    var name: String,

    var description: String,

    var uomId: Long,

    var uomAmount: Double,

    var ingredientId: Long
)