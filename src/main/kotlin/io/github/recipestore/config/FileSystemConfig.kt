package io.github.recipestore.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Paths

@Configuration
class FileSystemConfig {

    @Value("\${ingredients-images-path}")
    private lateinit var imagesPath: String

    @EventListener
    fun handleContextRefreshEvent(event: ContextRefreshedEvent) {

        val path = Paths.get(imagesPath)
        if (!Files.exists(path)) Files.createDirectories(path)
    }
}