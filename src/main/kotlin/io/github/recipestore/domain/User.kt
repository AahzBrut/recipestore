package io.github.recipestore.domain

import org.springframework.data.annotation.Id

data class User(
        @Id
        var id: Long,
        var name: String,
        var password: String,
        var roles: List<Role>
)