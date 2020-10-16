package io.github.recipestore.converter.reader

import io.github.recipestore.domain.Ingredient
import io.r2dbc.spi.Row
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class IngredientReaderConverter : Converter<Row, Ingredient> {

    override fun convert(source: Row): Ingredient = Ingredient(
        (source["INGREDIENT_ID"] as Int).toLong(),
        source["NAME"] as String,
        source["DESCRIPTION"] as String,
        (source["INGREDIENT_CATEGORY_ID"] as Int).toLong(),
        (source["USER_ID"] as Int).toLong(),
        source["CREATED"] as LocalDateTime,
        source["MODIFIED"] as LocalDateTime
    )
}