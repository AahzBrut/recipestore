package io.github.recipestore.dto.request

data class CategoryRequest(var name: String, var description: String, var parentCategoryId: Long?)