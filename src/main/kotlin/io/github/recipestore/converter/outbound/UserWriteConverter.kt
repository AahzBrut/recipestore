package io.github.recipestore.converter.outbound

import io.github.recipestore.domain.User
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.r2dbc.mapping.OutboundRow
import org.springframework.data.r2dbc.mapping.SettableValue
import org.springframework.data.relational.core.sql.SqlIdentifier

@WritingConverter
class UserWriteConverter : Converter<User, OutboundRow> {

    override fun convert(source: User): OutboundRow {
        val result = OutboundRow()
        source.id?.let { result[SqlIdentifier.quoted("USER_ID")] = SettableValue.from(it) }
        result[SqlIdentifier.quoted("NAME")] = SettableValue.from(source.name)
        result[SqlIdentifier.quoted("PASSWORD")] = SettableValue.from(source.password)

        return result
    }
}