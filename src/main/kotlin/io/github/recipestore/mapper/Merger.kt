package io.github.recipestore.mapper

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface Merger<T, S> {

    fun merge(target: Mono<T>, source: Flux<S>) : Mono<T>
}