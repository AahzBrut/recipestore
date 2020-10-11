package io.github.recipestore.config

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration

@Configuration
class R2DbcConfig(
    private val converters: List<Converter<*,*>>
): AbstractR2dbcConfiguration() {

    override fun connectionFactory(): ConnectionFactory {
        return ConnectionFactories.get("r2dbc:h2:file:///~/recipestore/db")
    }

    override fun getCustomConverters(): MutableList<Any> = converters.toMutableList()
}