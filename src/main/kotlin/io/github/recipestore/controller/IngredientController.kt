package io.github.recipestore.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.security.Principal


const val INGREDIENTS_PATH = "/api/v1/ingredients"

@RestController
class IngredientController {


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(INGREDIENTS_PATH)
    fun getAllIngredients(principal: Principal) : Flux<String> {


        return Flux
            .just("Ingredient1", "Ingredient2", "Ingredient3")
            .doOnSubscribe{ log("User: ${principal.name} in $INGREDIENTS_PATH") }
    }


    private fun log(message: String) {
        println(message)
    }
}