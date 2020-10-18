package io.github.recipestore.repository

import io.github.recipestore.domain.UOM
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface UomRepository: ReactiveCrudRepository<UOM, Long>