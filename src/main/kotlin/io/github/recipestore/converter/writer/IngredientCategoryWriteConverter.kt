package io.github.recipestore.converter.writer

import io.github.recipestore.domain.IngredientCategory
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.r2dbc.mapping.OutboundRow
import org.springframework.data.r2dbc.mapping.SettableValue
import org.springframework.data.relational.core.sql.SqlIdentifier
import org.springframework.stereotype.Service

@Service
@WritingConverter
class IngredientCategoryWriteConverter : Converter<IngredientCategory, OutboundRow> {

    override fun convert(source: IngredientCategory): OutboundRow = OutboundRow().also { row ->
        source.id?.let { row[SqlIdentifier.quoted("INGREDIENT_CATEGORY_ID")] = SettableValue.from(it) }
        row[SqlIdentifier.quoted("NAME")] = SettableValue.from(source.name)
        row[SqlIdentifier.quoted("DESCRIPTION")] = SettableValue.from(source.description)
        source.parentCategoryId?.let { row[SqlIdentifier.quoted("PARENT_CATEGORY_ID")] = SettableValue.from(it) }
        row[SqlIdentifier.quoted("USER_ID")] = SettableValue.from(source.userId)
    }
}