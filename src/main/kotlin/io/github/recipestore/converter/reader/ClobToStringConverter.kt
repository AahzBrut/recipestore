package io.github.recipestore.converter.reader

import io.r2dbc.spi.Clob
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.runBlocking
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Service

@Service
class ClobToStringConverter : Converter<Clob, String> {

    override fun convert(source: Clob): String {

        @Suppress("BlockingMethodInNonBlockingContext")
        return runBlocking {
            source
                .stream()
                .asFlow()
                .fold(StringBuilder()) { a, e ->
                    a.append(e)
                }
                .toString()
        }
    }
}