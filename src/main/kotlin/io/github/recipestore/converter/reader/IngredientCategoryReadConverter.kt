package io.github.recipestore.converter.reader

import io.github.recipestore.domain.IngredientCategory
import io.r2dbc.spi.Row
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@ReadingConverter
class IngredientCategoryReadConverter : Converter<Row, IngredientCategory> {

    override fun convert(source: Row): IngredientCategory =
        IngredientCategory(
            (source["INGREDIENT_CATEGORY_ID"] as Int).toLong(),
            source["NAME"] as String,
            source["DESCRIPTION"] as String,
            source["PARENT_CATEGORY_ID"]?.let { (source["PARENT_CATEGORY_ID"] as Int).toLong()},
            (source["USER_ID"] as Int).toLong(),
            source["CREATED"] as LocalDateTime,
            source["MODIFIED"] as LocalDateTime
        )
}