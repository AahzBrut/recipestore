package io.github.recipestore.config

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration

@Configuration
class R2DbcConfig(
    private val converters: List<Converter<*,*>>
): AbstractR2dbcConfiguration() {

    @Value("\${spring.r2dbc.url}")
    private lateinit var dbUrl: String

    override fun connectionFactory(): ConnectionFactory {
        return ConnectionFactories.get(dbUrl)
    }

    override fun getCustomConverters(): MutableList<Any> = converters.toMutableList()
}