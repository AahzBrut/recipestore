package io.github.recipestore.converter.reader

import io.github.recipestore.domain.RecipeCategory
import io.r2dbc.spi.Row
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class RecipeCategoryReadConverter : Converter<Row, RecipeCategory> {

    override fun convert(source: Row): RecipeCategory =
        RecipeCategory(
            (source["RECIPE_CATEGORY_ID"] as Int).toLong(),
            source["NAME"] as String,
            source["DESCRIPTION"] as String,
            source["PARENT_CATEGORY_ID"]?.let { (it as Int).toLong()},
            (source["USER_ID"] as Int).toLong(),
            source["CREATED"] as LocalDateTime,
            source["MODIFIED"] as LocalDateTime
        )
}