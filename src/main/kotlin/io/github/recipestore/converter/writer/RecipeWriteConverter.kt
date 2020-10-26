package io.github.recipestore.converter.writer

import io.github.recipestore.domain.Recipe
import org.springframework.core.convert.converter.Converter
import org.springframework.data.r2dbc.mapping.OutboundRow
import org.springframework.data.r2dbc.mapping.SettableValue.from
import org.springframework.data.relational.core.sql.SqlIdentifier.quoted
import org.springframework.stereotype.Service


@Service
class RecipeWriteConverter : Converter<Recipe, OutboundRow> {

    override fun convert(source: Recipe): OutboundRow = OutboundRow().also { result ->
        source.id?.let { result[quoted("RECIPE_ID")] = from(it) }
        result[quoted("NAME")] = from(source.name)
        result[quoted("DESCRIPTION")] = from(source.description)
        result[quoted("DIFFICULTY")] = from(source.difficulty)
        result[quoted("SERVINGS")] = from(source.numServings)
        result[quoted("SOURCE")] = from(source.source)
        result[quoted("URL")] = from(source.url)
        result[quoted("RECIPE_CATEGORY_ID")] = from(source.categoryId)
        result[quoted("USER_ID")] = from(source.userId)
    }
}