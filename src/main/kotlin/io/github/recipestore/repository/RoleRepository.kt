package io.github.recipestore.repository

import io.github.recipestore.domain.Role
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface RoleRepository : ReactiveCrudRepository<Role, Long>