package io.github.recipestore.converter.writer

import io.github.recipestore.domain.Ingredient
import org.springframework.core.convert.converter.Converter
import org.springframework.data.r2dbc.mapping.OutboundRow
import org.springframework.data.r2dbc.mapping.SettableValue
import org.springframework.data.relational.core.sql.SqlIdentifier
import org.springframework.stereotype.Service

@Service
class IngredientWriterConverter : Converter<Ingredient, OutboundRow> {

    override fun convert(source: Ingredient): OutboundRow {
        val result = OutboundRow()
        source.id?.let { result[SqlIdentifier.quoted("INGREDIENT_ID")] = SettableValue.from(it) }
        result[SqlIdentifier.quoted("NAME")] = SettableValue.from(source.name)
        result[SqlIdentifier.quoted("DESCRIPTION")] = SettableValue.from(source.description)
        result[SqlIdentifier.quoted("INGREDIENT_CATEGORY_ID")] = SettableValue.from(source.categoryId)
        result[SqlIdentifier.quoted("USER_ID")] = SettableValue.from(source.userId)

        return result
    }
}