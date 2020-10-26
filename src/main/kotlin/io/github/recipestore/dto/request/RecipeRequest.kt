package io.github.recipestore.dto.request

import io.github.recipestore.domain.RecipeDifficulties

data class RecipeRequest(
    var name: String,
    var description: String,
    var difficulty: RecipeDifficulties,
    var servings: Int,
    var source: String,
    var url: String,
    var categoryId: Long
)