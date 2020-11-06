package io.github.recipestore.config

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.connectionfactory.init.CompositeDatabasePopulator
import org.springframework.data.r2dbc.connectionfactory.init.ConnectionFactoryInitializer
import org.springframework.data.r2dbc.connectionfactory.init.ResourceDatabasePopulator


@Configuration
class R2DbcConfig(
    private val converters: List<Converter<*, *>>
) : AbstractR2dbcConfiguration() {

    @Value("\${spring.r2dbc.url}")
    private lateinit var dbUrl: String

    override fun connectionFactory(): ConnectionFactory {
        return ConnectionFactories.get(dbUrl)
    }

    @Bean
    fun initializer(@Qualifier("connectionFactory") connectionFactory: ConnectionFactory): ConnectionFactoryInitializer? {

        return ConnectionFactoryInitializer()
            .also {
                it.setConnectionFactory(connectionFactory)
                it.setDatabasePopulator(CompositeDatabasePopulator()
                    .also { populator ->
                        populator.addPopulators(ResourceDatabasePopulator(ClassPathResource("schema.sql")))
                    })
            }
    }

    override fun getCustomConverters(): MutableList<Any> = converters.toMutableList()
}