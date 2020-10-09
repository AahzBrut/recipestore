package io.github.recipestore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RecipeStoreApplication

fun main(args: Array<String>) {
    runApplication<RecipeStoreApplication>(*args)
}
