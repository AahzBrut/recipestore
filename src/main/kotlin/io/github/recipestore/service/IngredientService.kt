package io.github.recipestore.service

import io.github.recipestore.domain.Ingredient
import io.github.recipestore.dto.request.IngredientRequest
import io.github.recipestore.repository.IngredientCategoryRepository
import io.github.recipestore.repository.IngredientRepository
import io.github.recipestore.repository.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.io.File
import java.nio.channels.ByteChannel
import java.nio.channels.Channels
import java.nio.channels.FileChannel
import java.nio.file.Files
import java.nio.file.OpenOption
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

@Service
class IngredientService(
    private val repository: IngredientRepository,
    private val userRepository: UserRepository,
    private val ingredientCategoryRepository: IngredientCategoryRepository
) {

    @Value("\${ingredients-images-path}")
    private lateinit var imagesPath: String

    fun addIngredient(userName: String, request: IngredientRequest): Mono<Ingredient> =
        userRepository.findByName(userName)
            .flatMap { user ->
                repository
                    .save(
                        Ingredient(name = request.name, description = request.description, categoryId = request.categoryId, userId = user.id!!)
                    )
            }
            .flatMap {
                getIngredient(it.id!!)
            }

    fun getAllIngredients(): Flux<Ingredient> =
        repository.findAll()
            .flatMap { ingredient ->
                Mono.just(ingredient)
                    .zipWith(ingredientCategoryRepository.findById(ingredient.categoryId))
                    .map { tuple ->
                        tuple.t1.category = tuple.t2
                        tuple.t1
                    }
            }
            .flatMap { ingredient ->
                Mono.just(ingredient)
                    .zipWith(userRepository.findById(ingredient.userId))
                    .map { tuple ->
                        tuple.t1.user = tuple.t2
                        tuple.t1
                    }
            }

    fun getIngredient(id: Long): Mono<Ingredient> =
        repository.findById(id)
            .flatMap { ingredient ->
                Mono.just(ingredient)
                    .zipWith(ingredientCategoryRepository.findById(ingredient.categoryId))
                    .map { tuple ->
                        tuple.t1.category = tuple.t2
                        tuple.t1
                    }
            }
            .flatMap { ingredient ->
                Mono.just(ingredient)
                    .zipWith(userRepository.findById(ingredient.userId))
                    .map { tuple ->
                        tuple.t1.user = tuple.t2
                        tuple.t1
                    }
            }

    fun updateIngredient(userName: String, id: Long, request: IngredientRequest): Mono<Ingredient> =
        userRepository.findByName(userName)
            .flatMap {
                repository.updateIngredient(id, request.name, request.description, request.categoryId, it.id!!)
            }
            .flatMap {
                getIngredient(id)
            }

    fun deleteIngredient(id: Long): Mono<Void> = repository.deleteById(id)

    fun setImage(id: Long, image: Mono<FilePart>): Mono<Void> =
        image
            .flatMap {filePart ->
                DataBufferUtils
                    .write(filePart.content().cache(), createFileAndGetChannel(Path.of("$imagesPath/$id.${getExtensionFromFileName(filePart.filename())}")))
                    .map {dataBuffer ->
                        DataBufferUtils.release(dataBuffer)
                    }
                Mono.empty()
            }

    private fun getExtensionFromFileName(fileName: String): String =
        fileName.split(".")[1]

    private fun createFileAndGetChannel(path: Path): ByteChannel {
        if (!Files.exists(path.parent)) Files.createDirectories(path.parent)
        if (!Files.exists(path)) Files.createFile(path)
        return Files.newByteChannel(path, StandardOpenOption.READ, StandardOpenOption.WRITE)
    }

    fun getImage(imageId: Long): Mono<Resource> =
        Mono.just(FileSystemResource("$imagesPath/$imageId.png"))
}