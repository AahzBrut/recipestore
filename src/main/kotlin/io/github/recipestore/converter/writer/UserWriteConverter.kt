package io.github.recipestore.converter.writer

import io.github.recipestore.domain.User
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.r2dbc.mapping.OutboundRow
import org.springframework.data.r2dbc.mapping.SettableValue.from
import org.springframework.data.relational.core.sql.SqlIdentifier.quoted
import org.springframework.stereotype.Service

@Service
@WritingConverter
class UserWriteConverter : Converter<User, OutboundRow> {

    override fun convert(source: User): OutboundRow {
        val result = OutboundRow()
        source.id?.let { result[quoted("USER_ID")] = from(it) }
        result[quoted("NAME")] = from(source.name)
        result[quoted("PASSWORD")] = from(source.password)

        return result
    }
}