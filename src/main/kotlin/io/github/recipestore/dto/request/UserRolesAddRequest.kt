package io.github.recipestore.dto.request

import io.github.recipestore.domain.Roles

data class UserRolesAddRequest(var roles: Set<Roles>)