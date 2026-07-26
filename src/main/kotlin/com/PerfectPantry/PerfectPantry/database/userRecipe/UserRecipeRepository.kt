package com.PerfectPantry.PerfectPantry.database.userRecipe

import com.PerfectPantry.PerfectPantry.model.RecipeIngredient
import com.PerfectPantry.PerfectPantry.model.UserRecipe
import org.springframework.http.HttpStatusCode
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.Optional

@Service
class UserRecipeRepository(
    private val jdbcClient: JdbcClient
) {

    fun getAllUserRecipes(): List<UserRecipe> =
        jdbcClient.sql("SELECT * FROM usr_recipe")
            .query(UserRecipeRowMapper())
            .list()

    fun getUserRecipeByIds(recipeId: Int, userId: Int): Optional<UserRecipe> =
        jdbcClient.sql("SELECT * FROM usr_recipe WHERE recipe_id = :recipe_id and usr_id = :user_id")
            .param("recipe_id", recipeId)
            .param("user_id", userId)
            .query(UserRecipeRowMapper())
            .optional()

    fun getUserRecipeByUser(userId: Int): List<UserRecipe> =
        jdbcClient.sql("SELECT * FROM usr_recipe WHERE usr_id = :user_id")
            .param("user_id", userId)
            .query(UserRecipeRowMapper())
            .list()

    fun getUserRecipeByRecipe(recipeId: Int): List<UserRecipe> =
        jdbcClient.sql("SELECT * FROM usr_recipe WHERE recipe_id = :recipe_id")
            .param("recipe_id", recipeId)
            .query(UserRecipeRowMapper())
            .list()

    fun createUserRecipe(userRecipe: UserRecipe): UserRecipe? {
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        val update =
            jdbcClient.sql("""
                INSERT INTO usr_recipe(RECIPE_ID, USR_ID, NOTES) 
                VALUES (:recipe_id, :user_id, :notes)
            """.trimIndent())
                .param("recipe_id", userRecipe.recipeId)
                .param("user_id", userRecipe.userId)
                .param("notes", userRecipe.notes)
                .update(keyHolder)

        if (update == 1) {
            val potentialUserRecipe = getUserRecipeByIds(userRecipe.recipeId, userRecipe.userId)
            return if (potentialUserRecipe.isPresent) {
                potentialUserRecipe.get()
            } else {
                throw ResponseStatusException(HttpStatusCode.valueOf(404))
            }
        } else {
            throw ResponseStatusException(HttpStatusCode.valueOf(500), "Unable to create User Recipe")
        }
    }
}