package io.github.recipestore.dto.request

data class RecipeStepAddRequest (

    var name:String,

    var description: String,

    var ordinal: Int,

    var ingredients: List<RecipeStepIngredientAddRequest> = mutableListOf()
)