package io.github.recipestore.converter.reader

import io.r2dbc.spi.Clob
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Service
import reactor.kotlin.core.publisher.toMono

@Service
class ClobToStringConverter : Converter<Clob, String> {

    override fun convert(source: Clob): String {
        val result = StringBuilder()

        return source
            .stream()
            .toMono()
            .map(result::append)
            .block().toString()
    }
}