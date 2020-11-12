package io.github.recipestore.service

import io.github.recipestore.domain.Ingredient
import io.github.recipestore.dto.request.IngredientRequest
import io.github.recipestore.mapper.IngredientAddCategory
import io.github.recipestore.mapper.IngredientAddUser
import io.github.recipestore.repository.IngredientCategoryRepository
import io.github.recipestore.repository.IngredientRepository
import io.github.recipestore.repository.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.nio.file.Path

@Service
class IngredientService(
    private val repository: IngredientRepository,
    private val userRepository: UserRepository,
    private val ingredientCategoryRepository: IngredientCategoryRepository,
    private val ingredientAddCategory: IngredientAddCategory,
    private val ingredientAddUser: IngredientAddUser
) {

    @Value("\${ingredients-images-path}")
    private lateinit var imagesPath: String

    fun addIngredient(userName: String, request: IngredientRequest): Mono<Ingredient> =
        userRepository.findByName(userName)
            .flatMap { user ->
                repository
                    .save(Ingredient(name = request.name, description = request.description, categoryId = request.categoryId, userId = user.id!!))
            }.flatMap { getIngredient(it.id!!) }

    fun getAllIngredients(): Flux<Ingredient> =
        repository.findAll()
            .flatMap { ingredientAddCategory.merge(Mono.just(it), ingredientCategoryRepository.findById(it.categoryId)) }
            .flatMap { ingredientAddUser.merge(Mono.just(it), userRepository.findById(it.userId)) }

    fun getIngredient(id: Long): Mono<Ingredient> =
        repository.findById(id)
            .flatMap { ingredientAddCategory.merge(Mono.just(it), ingredientCategoryRepository.findById(it.categoryId)) }
            .flatMap { ingredientAddUser.merge(Mono.just(it), userRepository.findById(it.userId)) }

    fun updateIngredient(userName: String, id: Long, request: IngredientRequest): Mono<Ingredient> =
        userRepository.findByName(userName)
            .flatMap { repository.updateIngredient(id, request.name, request.description, request.categoryId, it.id!!) }
            .flatMap { getIngredient(id) }

    fun deleteIngredient(id: Long): Mono<Void> = repository.deleteById(id)

    fun setImage(id: Long, image: Mono<FilePart>): Mono<Void> =
        image
            .flatMap { filePart ->
                filePart.transferTo(Path.of("$imagesPath/$id.${getExtensionFromFileName(filePart.filename())}"))
            }

    private fun getExtensionFromFileName(fileName: String): String =
        fileName.split(".")[1]

    fun getImage(imageId: Long): Mono<Resource> =
        Mono.just(FileSystemResource("$imagesPath/$imageId.png"))
}