package io.github.recipestore.converter.reader

import io.github.recipestore.domain.Recipe
import io.github.recipestore.domain.RecipeDifficulties
import io.r2dbc.spi.Row
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class RecipeReadConverter : Converter<Row, Recipe> {

    override fun convert(source: Row): Recipe =
        Recipe(
        (source["RECIPE_ID"] as Int).toLong(),
        source["NAME"] as String,
        source["DESCRIPTION"] as String,
        RecipeDifficulties.valueOf(source["DIFFICULTY"] as String),
        source["SERVINGS"] as Int,
        source["SOURCE"] as String,
        (source["URL"] as String),
        (source["USER_ID"] as Int).toLong(),
        (source["RECIPE_CATEGORY_ID"] as Int).toLong(),
        source["CREATED"] as LocalDateTime,
        source["MODIFIED"] as LocalDateTime,
    )
}