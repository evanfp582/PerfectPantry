package com.PerfectPantry.PerfectPantry.database.userIngredient

import com.PerfectPantry.PerfectPantry.database.userRecipe.UserRecipeRowMapper
import com.PerfectPantry.PerfectPantry.model.RecipeIngredient
import com.PerfectPantry.PerfectPantry.model.UserIngredient
import com.PerfectPantry.PerfectPantry.model.UserRecipe
import org.springframework.http.HttpStatusCode
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

import java.util.Optional

@Service
class UserIngredientRepository(
    private val jdbcClient: JdbcClient
) {

    fun getAllUserIngredients(): List<UserIngredient> =
        jdbcClient.sql("SELECT * FROM usr_ingredient")
            .query(UserIngredientRowMapper())
            .list()


    fun getUserIngredientsByIds(ingredientId: Int, userId: Int): Optional<UserIngredient> =
        jdbcClient.sql("SELECT * FROM usr_ingredient WHERE ingredient_id = :ingredient_id and usr_id = :user_id")
            .param("ingredient_id", ingredientId)
            .param("user_id", userId)
            .query(UserIngredientRowMapper())
            .optional()

    fun getUserIngredientsByUser(userId: Int): List<UserIngredient> =
        jdbcClient.sql("SELECT * FROM usr_ingredient WHERE usr_id = :user_id")
            .param("user_id", userId)
            .query(UserIngredientRowMapper())
            .list()

    fun getUserIngredientsByIngredient(ingredientId: Int): List<UserIngredient> =
        jdbcClient.sql("SELECT * FROM usr_ingredient WHERE ingredient_id = :ingredient_id")
            .param("ingredient_id", ingredientId)
            .query(UserIngredientRowMapper())
            .list()

    fun createUserIngredient(userIngredient: UserIngredient): UserIngredient? {
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        val update =
            jdbcClient.sql("""
                INSERT INTO usr_ingredient(INGREDIENT_ID, USR_ID, NOTES) 
                VALUES (:recipe_id, :user_id, :notes)
            """.trimIndent())
                .param("ingredient_id", userIngredient.ingredientId)
                .param("user_id", userIngredient.userId)
                .param("notes", userIngredient.notes)
                .update(keyHolder)

        if (update == 1) {
            val potentialUserIngredient = getUserIngredientsByIds(userIngredient.ingredientId, userIngredient.userId)
            return if (potentialUserIngredient.isPresent) {
                potentialUserIngredient.get()
            } else {
                throw ResponseStatusException(HttpStatusCode.valueOf(404))
            }
        } else {
            throw ResponseStatusException(HttpStatusCode.valueOf(500), "Unable to create User Ingredient")
        }
    }

}