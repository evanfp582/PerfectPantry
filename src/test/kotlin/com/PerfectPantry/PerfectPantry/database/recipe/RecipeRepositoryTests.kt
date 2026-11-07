
package com.PerfectPantry.PerfectPantry.database.user

import com.PerfectPantry.PerfectPantry.database.recipe.RecipeRepository
import com.PerfectPantry.PerfectPantry.model.Instructions
import com.PerfectPantry.PerfectPantry.model.NewRecipe
import com.PerfectPantry.PerfectPantry.model.Recipe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.jdbc.Sql
import org.springframework.web.server.ResponseStatusException
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.test.assertEquals


@SpringBootTest
@Testcontainers
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = ["/sql/recipes.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class RecipeRepositoryTests {

    companion object {
        @Container
        @ServiceConnection
        @JvmStatic
        val postgres = PostgreSQLContainer<Nothing>("postgres:18")
    }

    @Autowired
    private lateinit var recipeRepository: RecipeRepository

    @Test
    fun `get recipes`() {
        val recipes = recipeRepository.getRecipesFromDb()
        val expectedRecipes = listOf(
            Recipe(1,"Iced Water", Instructions(listOf("Add ice to water")),"Best drink", "1 min", 1.0, "Evan Fisher-Perez", url = null ),
            Recipe(2,"Grilled Cheese", Instructions(listOf("Take bread and toast it", "Put cheese in between bread")),"Yummy sandwich served best with tomato soup", "4 min", 1.0, "Evan Fisher-Perez", url = null )
        )
        assertEquals(expectedRecipes, recipes, "Recipe list of $recipes did not match expected $expectedRecipes")
    }

    @Test
    fun `get recipe by present id`() {
        val recipe = recipeRepository.getRecipe(2)
        val expectedRecipe = Recipe(2,"Grilled Cheese", Instructions(listOf("Take bread and toast it", "Put cheese in between bread")),"Yummy sandwich served best with tomato soup", "4 min", 1.0, "Evan Fisher-Perez", url = null )

        assertEquals(expectedRecipe, recipe.get(), "Recipe of $recipe did not match expected $expectedRecipe")
    }

    @Test
    fun `get recipe by absent id`() {
        val recipe = recipeRepository.getRecipe(5)

        assertEquals(Optional.empty<Recipe>(), recipe, "Recipe of $recipe did not match expected empty value")
    }

    @Test
    fun `recipe fuzzy search by exact match`() {
        val recipes = recipeRepository.searchRecipe("Iced Water")
        val expectedRecipes = listOf(
            Recipe(1,"Iced Water", Instructions(listOf("Add ice to water")),"Best drink", "1 min", 1.0, "Evan Fisher-Perez", url = null )
        )
        assertEquals(expectedRecipes, recipes, "User list of $recipes did not match expected $expectedRecipes")
    }

    @Test
    fun `recipe fuzzy search by rough match`() {
        val recipes = recipeRepository.searchRecipe("Icd Wat")
        val expectedRecipes = listOf(
            Recipe(1,"Iced Water", Instructions(listOf("Add ice to water")),"Best drink", "1 min", 1.0, "Evan Fisher-Perez", url = null )
        )
        assertEquals(expectedRecipes, recipes, "User list of $recipes did not match expected $expectedRecipes")
    }

    @Test
    fun `search by present name`() {
        val recipe = recipeRepository.existingRecipe("Iced Water", source = null)
        val expectedRecipe= Recipe(1,"Iced Water", Instructions(listOf("Add ice to water")),"Best drink", "1 min", 1.0, "Evan Fisher-Perez", url = null )

        assertEquals(expectedRecipe, recipe.get(), "Recipe of $recipe did not match expected $expectedRecipe")
    }

    @Test
    fun `search by present name with a wrong source`() {
        val recipe = recipeRepository.existingRecipe("Iced Water", source = "Food.com")
        assertEquals(Optional.empty<Recipe>(), recipe, "Recipe of $recipe did not match expected null recipe")
    }

    @Test
    fun `search by absent name`() {
        val recipe = recipeRepository.existingRecipe("Baba Ganoush", source = null)
        assertEquals(Optional.empty<Recipe>(), recipe, "Recipe of $recipe did not match expected null recipe")
    }

    @Test
    fun `create recipe`() {
        val recipe = recipeRepository.createRecipe(NewRecipe(
            "PB&J",
            Instructions(listOf("Apply PB on 1 slice of bread", "Apply jelly on other slice of bread", "Combine slices of bread")),
            description = "A classic sandwich",
            time = "1 min",
            yield = 1.0,
            source = "Evan Fisher-Perez",
            url = null
        ))

        val expectedRecipe = Recipe(
            id = 3,
            name = "PB&J",
            instructions = Instructions(listOf("Apply PB on 1 slice of bread", "Apply jelly on other slice of bread", "Combine slices of bread")),
            description = "A classic sandwich",
            time = "1 min",
            yield = 1.0,
            source = "Evan Fisher-Perez",
            url = null
        )
        assertEquals(expectedRecipe, recipe, "User list of $recipe did not match expected $expectedRecipe")
    }

    @Test
    fun `get recipes by ingredient`() {
        val recipes = recipeRepository.getRecipesByIngredient(2)
        val expectedRecipes = listOf(
            Recipe(1,"Iced Water", Instructions(listOf("Add ice to water")),"Best drink", "1 min", 1.0, "Evan Fisher-Perez", url = null ),
        )
        assertEquals(expectedRecipes, recipes, "Recipe list of $recipes did not match expected $expectedRecipes")
    }

    @Test
    fun `get recipes by user`() {
        val recipes = recipeRepository.getRecipesByUser(1)
        val expectedRecipes = listOf(
            Recipe(2,"Grilled Cheese", Instructions(listOf("Take bread and toast it", "Put cheese in between bread")),"Yummy sandwich served best with tomato soup", "4 min", 1.0, "Evan Fisher-Perez", url = null )
        )

        assertEquals(expectedRecipes, recipes, "Recipes list of $recipes did not match expected $expectedRecipes")
    }

    @Test
    fun `delete recipe`() {

//        val recipes = recipeRepository.getRecipesFromDb()
//        val expectedRecipes = listOf(
//            Recipe(2,"Grilled Cheese", Instructions(listOf("Take bread and toast it", "Put cheese in between bread")),"Yummy sandwich served best with tomato soup", "4 min", 1.0, "Evan Fisher-Perez", url = null )
//        )
        assertThrows<ResponseStatusException> { recipeRepository.deleteRecipe(1) }
        // This should fail because this is just the basic delete in recipe repository.
        // It fails because you are deleting a primary key when recipe ingredient expects this to be here
        // In the future I will implement a more advanced test inside User Service tests
    }

    @Test
    fun `update recipe`() {
        val recipe = recipeRepository.updateRecipe(
            Recipe(2,"THE BEST Grilled Cheese", Instructions(listOf("Take bread and toast it", "Put cheese in between bread")),"Yummy sandwich served best with tomato soup", "4 min", 1.0, "Evan Fisher-Perez", url = null )
        )
        val expectedRecipe = Recipe(2,"THE BEST Grilled Cheese", Instructions(listOf("Take bread and toast it", "Put cheese in between bread")),"Yummy sandwich served best with tomato soup", "4 min", 1.0, "Evan Fisher-Perez", url = null )
        assertEquals(expectedRecipe, recipe, "User list of $recipe did not match expected $expectedRecipe")
    }

    @Test
    fun `update recipe and check db`() {
        val recipe = recipeRepository.updateRecipe(
            Recipe(2,"THE BEST Grilled Cheese", Instructions(listOf("Take bread and toast it", "Put cheese in between bread")),"Yummy sandwich served best with tomato soup", "4 min", 1.0, "Evan Fisher-Perez", url = null )
        )
        val expectedRecipe = recipeRepository.getRecipe(2)
        assertEquals(expectedRecipe.get(), recipe, "User list of $recipe did not match expected $expectedRecipe")
    }

}
