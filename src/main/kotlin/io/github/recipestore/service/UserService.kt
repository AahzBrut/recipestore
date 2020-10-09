package io.github.recipestore.service

import io.github.recipestore.dto.request.LoginRequest

interface UserService {

    fun login(request: LoginRequest): String
}