package io.github.recipestore.domain

data class User(
        var id: Long,
        var userName: String,
        var password: String,
        var roles: List<Role>
)