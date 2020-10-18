package io.github.recipestore.service

import io.github.recipestore.domain.UOM
import io.github.recipestore.repository.UomRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class UomService(
    private val repository: UomRepository
) {

    fun getUOMById(uomId: Long): Mono<UOM> =
        repository.findById(uomId)

    fun getAllUOMs(): Flux<UOM> =
        repository.findAll()
}
