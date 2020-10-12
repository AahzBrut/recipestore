package io.github.recipestore.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.security.Principal


const val INGREDIENTS_GET_ALL = "/api/v1/ingredients"

@RestController
class IngredientController {


    @GetMapping(INGREDIENTS_GET_ALL)
    fun getAllIngredients(principal: Principal) : Flux<String> {


        return Flux
            .just("Ingredient1", "Ingredient2", "Ingredient3")
            .doOnSubscribe{ log("$principal in $INGREDIENTS_GET_ALL") }
    }


    private fun log(message: String) {
        println(message)
    }
}