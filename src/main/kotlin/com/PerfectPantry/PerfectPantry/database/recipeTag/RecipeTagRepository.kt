package com.PerfectPantry.PerfectPantry.database.recipeTag

import com.PerfectPantry.PerfectPantry.model.RecipeTag
import org.springframework.http.HttpStatusCode
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

import java.util.Optional

@Service
class RecipeTagRepository(
    private val jdbcClient: JdbcClient
) {

    fun getAllRecipeTags(): List<RecipeTag> =
        jdbcClient.sql("SELECT * FROM recipe_tag")
            .query(RecipeTagRowMapper())
            .list()

    fun getRecipeTag(recipeId: Int, tagId: Int): Optional<RecipeTag> =
        jdbcClient.sql("""
            SELECT * FROM recipe_tag
            WHERE recipe_id = :recipe_id AND tag_id = :tag_id
        """.trimIndent())
            .param("recipe_id", recipeId)
            .param("tag_id", tagId)
            .query(RecipeTagRowMapper())
            .optional()

    fun createRecipeTag(recipeTag: RecipeTag): RecipeTag? {
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        val update =
            jdbcClient.sql("""
                INSERT INTO recipe_tag(RECIPE_ID, TAG_ID) 
                VALUES (:recipe_id, :tag_id)
            """.trimIndent())
                .param("recipe_id", recipeTag.recipeId)
                .param("tag_id", recipeTag.tagId)
                .update(keyHolder)

        if (update == 1) {
            val potentialRecipeTag = getRecipeTag(recipeTag.recipeId, recipeTag.tagId)
            return if (potentialRecipeTag.isPresent) {
                potentialRecipeTag.get()
            } else {
                throw ResponseStatusException(HttpStatusCode.valueOf(404))
            }
        } else {
//            logger.error { "Unable to create recipe tag" }
            throw ResponseStatusException(HttpStatusCode.valueOf(500))
        }
    }

    fun deleteRecipeTag(recipeId: Int, tagId: Int) {
        val update = try {
            jdbcClient.sql("DELETE FROM recipe_tag WHERE recipe_id = :recipe_id AND tag_id = :tag_id")
                .param("recipe_id", recipeId)
                .param("tag_id", tagId)
                .update()
        } catch (e: Exception) {
//            logger.error(e) { "Unable to delete recipe tag due to error with the query or connection" }
            throw ResponseStatusException(HttpStatusCode.valueOf(500))
        }
        if (update == 1) {
            return
        }
    }

    fun deleteRecipeTagByRecipe(recipeId: Int) {
        val update = try {
            jdbcClient.sql("DELETE FROM recipe_tag WHERE recipe_id = :recipe_id")
                .param("recipe_id", recipeId)
                .update()
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatusCode.valueOf(500))
        }
        if (update == 1) {
            return
        }
    }

    fun deleteRecipeTagByTag(tagId: Int) {
        val update = try {
            jdbcClient.sql("DELETE FROM recipe_tag WHERE tag_id = :tag_id")
                .param("tag_id", tagId)
                .update()
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatusCode.valueOf(500))
        }
        if (update == 1) {
            return
        }
    }
}