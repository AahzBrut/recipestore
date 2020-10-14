package io.github.recipestore.converter.reader

import io.github.recipestore.domain.User
import io.r2dbc.spi.Row
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@ReadingConverter
class UserReadConverter : Converter<Row, User>{

    override fun convert(source: Row): User =
        User(
            (source["USER_ID"] as Int).toLong(),
            source["NAME"] as String,
            source["PASSWORD"] as String,
            source["CREATED"] as LocalDateTime,
            source["MODIFIED"] as LocalDateTime
        )
}