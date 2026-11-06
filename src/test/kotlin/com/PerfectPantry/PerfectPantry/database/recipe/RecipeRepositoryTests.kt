
package com.PerfectPantry.PerfectPantry.database.user

import com.PerfectPantry.PerfectPantry.database.recipe.RecipeRepository
import com.PerfectPantry.PerfectPantry.model.Instructions
import com.PerfectPantry.PerfectPantry.model.NewUser
import com.PerfectPantry.PerfectPantry.model.Recipe
import com.PerfectPantry.PerfectPantry.model.Recipes
import com.PerfectPantry.PerfectPantry.model.User
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.jdbc.Sql
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.*
import kotlin.test.Ignore
import kotlin.test.assertEquals

@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = ["/sql/recipes.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class RecipeRepositoryTests {

    companion object {
        @Container
        @ServiceConnection
        @JvmStatic
        val postgres = PostgreSQLContainer<Nothing>("postgres:18")

        @JvmStatic
        fun registerDatabaseProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgres::getJdbcUrl)
            registry.add("spring.datasource.username", postgres::getUsername)
            registry.add("spring.datasource.password", postgres::getPassword)
        }
    }

    @Autowired
    private lateinit var recipeRepository: RecipeRepository

    // For Code coverage reasons, I am going to handle each function in order of it appearing in UserRepository
    @Test
    fun `get recipes`() {
        val recipes = recipeRepository.getRecipesFromDb()
        val expectedRecipes = listOf(
            Recipe(1,"Iced Water", Instructions(listOf("Add ice to water")),"Best drink", "1 min", 1.0, "Evan Fisher-Perez", url = null )
        )
        println(recipes)
        println(expectedRecipes)
        assertEquals(expectedRecipes, recipes, "Recipe list of $recipes did not match expected $expectedRecipes")
    }

    @Ignore
    @Test
    fun `get recipe by id`() {
        val users = recipeRepository.getRecipe(1)
        val expectedUsers = listOf(
            User(1, "Judytm2001"),
            User(2, "spike8pizza"),
            User(3, "ethanfp582")
        )
//        assertEquals(expectedUsers, users, "User list of $users did not match expected $expectedUsers")
    }

    @Ignore
    @Test
    fun `fuzzy search recipes`() {
        val users = recipeRepository.searchRecipe("search")
        val expectedUsers = listOf(
            User(1, "Judytm2001"),
            User(2, "spike8pizza"),
            User(3, "ethanfp582")
        )
//        assertEquals(expectedUsers, users, "User list of $users did not match expected $expectedUsers")
    }

    @Ignore
    @Test
    fun `search by name`() {
        val users = recipeRepository.getRecipesFromDb()
        val expectedUsers = listOf(
            User(1, "Judytm2001"),
            User(2, "spike8pizza"),
            User(3, "ethanfp582")
        )
//        assertEquals(expectedUsers, users, "User list of $users did not match expected $expectedUsers")
    }

    @Ignore
    @Test
    fun `search by name and source`() {
        val users = recipeRepository.getRecipesFromDb()
        val expectedUsers = listOf(
            User(1, "Judytm2001"),
            User(2, "spike8pizza"),
            User(3, "ethanfp582")
        )
//        assertEquals(expectedUsers, users, "User list of $users did not match expected $expectedUsers")
    }

    @Ignore
    @Test
    fun `create recipe`() {
        val users = recipeRepository.getRecipesFromDb()
        val expectedUsers = listOf(
            User(1, "Judytm2001"),
            User(2, "spike8pizza"),
            User(3, "ethanfp582")
        )
//        assertEquals(expectedUsers, users, "User list of $users did not match expected $expectedUsers")
    }

    @Ignore
    @Test
    fun `delete recipe`() {
        val users = recipeRepository.getRecipesFromDb()
        val expectedUsers = listOf(
            User(1, "Judytm2001"),
            User(2, "spike8pizza"),
            User(3, "ethanfp582")
        )
//        assertEquals(expectedUsers, users, "User list of $users did not match expected $expectedUsers")
    }

    @Ignore
    @Test
    fun `update recipe`() {
        val users = recipeRepository.getRecipesFromDb()
        val expectedUsers = listOf(
            User(1, "Judytm2001"),
            User(2, "spike8pizza"),
            User(3, "ethanfp582")
        )
//        assertEquals(expectedUsers, users, "User list of $users did not match expected $expectedUsers")
    }

    @Ignore
    @Test
    fun `get recipes by ingredient`() {
        val users = recipeRepository.getRecipesFromDb()
        val expectedUsers = listOf(
            User(1, "Judytm2001"),
            User(2, "spike8pizza"),
            User(3, "ethanfp582")
        )
//        assertEquals(expectedUsers, users, "User list of $users did not match expected $expectedUsers")
    }

    @Ignore
    @Test
    fun `get recipes by user`() {
        val users = recipeRepository.getRecipesFromDb()
        val expectedUsers = listOf(
            User(1, "Judytm2001"),
            User(2, "spike8pizza"),
            User(3, "ethanfp582")
        )
//        assertEquals(expectedUsers, users, "User list of $users did not match expected $expectedUsers")
    }

}
