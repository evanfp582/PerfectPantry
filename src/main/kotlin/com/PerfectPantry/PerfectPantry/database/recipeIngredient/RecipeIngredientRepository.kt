package com.PerfectPantry.PerfectPantry.database.recipeIngredient

import com.PerfectPantry.PerfectPantry.database.ingredient.IngredientRowMapper
import com.PerfectPantry.PerfectPantry.database.recipe.RecipeRowMapper
import com.PerfectPantry.PerfectPantry.model.NewRecipe
import com.PerfectPantry.PerfectPantry.model.Recipe
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

    fun createRecipeIngredientById(recipeIngredient: RecipeIngredient): RecipeIngredient? {
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        val update =
            jdbcClient.sql("""
                INSERT INTO RecipeIngredient(RECIPE_ID, INGREDIENT_ID, QUANTITY) 
                VALUES (:recipe_id, :ingredient_id, :quantity)
            """.trimIndent())
                .param("ingredient_id", recipeIngredient.ingredientId)
                .param("recipe_id", recipeIngredient.recipeId)
                .param("quantity", recipeIngredient.quantity)
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
            jdbcClient.sql("DELETE FROM RECIPE WHERE ingredient_id = :ingredient_id AND recipe_id = :recipe_id")
                .param("ingredient_id", ingredientId)
                .param("recipe_id", recipeId)
                .update()
        } catch (e: Exception) {
//            logger.error(e) { "Unable to delete person due to error with the query or connection" }
            throw ResponseStatusException(HttpStatusCode.valueOf(500))
        }
        if (update == 1) {
            return
        } else {
//            logger.error { "Could not find recipe with id $id" }
            throw ResponseStatusException(HttpStatusCode.valueOf(404))
        }
    }
}