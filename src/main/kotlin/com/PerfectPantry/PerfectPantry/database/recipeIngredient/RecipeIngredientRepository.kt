package com.PerfectPantry.PerfectPantry.database.recipeIngredient

import com.PerfectPantry.PerfectPantry.model.RecipeIngredient
import org.springframework.http.HttpStatusCode
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

import java.util.Optional

@Service
class RecipeIngredientRepository(
    private val jdbcClient: JdbcClient
) {

    fun getAllRecipesIngredients(): List<RecipeIngredient> =
        jdbcClient.sql("SELECT * FROM recipe_ingredient")
            .query(RecipeIngredientRowMapper())
            .list()

    fun getRecipeIngredient(ingredientId: Int, recipeId: Int): Optional<RecipeIngredient> =
        jdbcClient.sql("""
            SELECT * FROM recipe_ingredient
            WHERE ingredient_id = :ingredient_id AND recipe_id = :recipe_id
        """.trimIndent())
            .param("ingredient_id", ingredientId)
            .param("recipe_id", recipeId)
            .query(RecipeIngredientRowMapper())
            .optional()

    fun getRecipeIngredientsByRecipe(recipeId: Int): Optional<RecipeIngredient> =
        jdbcClient.sql("""
            SELECT * FROM recipe_ingredient
            WHERE recipe_id = :recipe_id
        """.trimIndent())
            .param("recipe_id", recipeId)
            .query(RecipeIngredientRowMapper())
            .optional()

    fun createRecipeIngredientById(recipeIngredient: RecipeIngredient): RecipeIngredient? {
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        val update =
            jdbcClient.sql("""
                INSERT INTO recipe_ingredient(RECIPE_ID, INGREDIENT_ID, QUANTITY, UNIT) 
                VALUES (:recipe_id, :ingredient_id, :quantity, :unit::measurements)
            """.trimIndent())
                .param("ingredient_id", recipeIngredient.ingredientId)
                .param("recipe_id", recipeIngredient.recipeId)
                .param("quantity", recipeIngredient.quantity)
                .param("unit", recipeIngredient.unit.name.lowercase())
                .update(keyHolder)

        if (update == 1) {
            val potentialRecipeIngredient = getRecipeIngredient(recipeIngredient.ingredientId, recipeIngredient.recipeId)
            return if (potentialRecipeIngredient.isPresent) {
                potentialRecipeIngredient.get()
            } else {
                throw ResponseStatusException(HttpStatusCode.valueOf(404))
            }
        } else {
//            logger.error { "Unable to create recipe $recipe" }
            throw ResponseStatusException(HttpStatusCode.valueOf(500))
        }
    }

    fun deleteRecipeIngredient(ingredientId: Int, recipeId: Int) {
        val update = try {
            jdbcClient.sql("DELETE FROM recipe_ingredient WHERE ingredient_id = :ingredient_id AND recipe_id = :recipe_id")
                .param("ingredient_id", ingredientId)
                .param("recipe_id", recipeId)
                .update()
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatusCode.valueOf(500))
        }
        if (update == 1) {
            return
        }
    }

    fun deleteRecipeIngredientByRecipe(recipeId: Int) {
        val update = try {
            jdbcClient.sql("DELETE FROM recipe_ingredient WHERE recipe_id = :recipe_id")
                .param("recipe_id", recipeId)
                .update()
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatusCode.valueOf(500))
        }
        if (update == 1) {
            return
        }
    }

    fun deleteRecipeIngredientByIngredient(ingredientId: Int) {
        val update = try {
            jdbcClient.sql("DELETE FROM recipe_ingredient WHERE ingredient_id = :ingredient_id")
                .param("ingredient_id", ingredientId)
                .update()
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatusCode.valueOf(500))
        }
        if (update == 1) {
            return
        }
    }
}