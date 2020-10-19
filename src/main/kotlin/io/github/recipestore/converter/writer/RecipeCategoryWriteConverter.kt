package io.github.recipestore.converter.writer

import io.github.recipestore.domain.RecipeCategory
import org.springframework.core.convert.converter.Converter
import org.springframework.data.r2dbc.mapping.OutboundRow
import org.springframework.data.r2dbc.mapping.SettableValue
import org.springframework.data.relational.core.sql.SqlIdentifier
import org.springframework.stereotype.Service

@Service
class RecipeCategoryWriteConverter : Converter<RecipeCategory, OutboundRow> {

    override fun convert(source: RecipeCategory): OutboundRow = OutboundRow().also { row ->
        source.id?.let { row[SqlIdentifier.quoted("RECIPE_CATEGORY_ID")] = SettableValue.from(it) }
        row[SqlIdentifier.quoted("NAME")] = SettableValue.from(source.name)
        row[SqlIdentifier.quoted("DESCRIPTION")] = SettableValue.from(source.description)
        source.parentCategoryId?.let { row[SqlIdentifier.quoted("PARENT_CATEGORY_ID")] = SettableValue.from(it) }
        row[SqlIdentifier.quoted("USER_ID")] = SettableValue.from(source.userId)
    }
}