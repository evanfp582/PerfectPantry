package com.PerfectPantry.PerfectPantry.database.tag

import com.PerfectPantry.PerfectPantry.database.recipe.RecipeRowMapper
import com.PerfectPantry.PerfectPantry.model.NewRecipe
import com.PerfectPantry.PerfectPantry.model.NewTag
import com.PerfectPantry.PerfectPantry.model.Recipe
import com.PerfectPantry.PerfectPantry.model.Tag
import org.springframework.http.HttpStatusCode
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import tools.jackson.module.kotlin.jacksonObjectMapper

import java.util.Optional

@Service
class TagRepository(
    private val jdbcClient: JdbcClient
) {

    fun getRecipes(): List<Tag> =
        jdbcClient.sql("SELECT * FROM TAG")
            .query(TagRowMapper())
            .list()

    fun getTag(id: Int): Optional<Tag> =
        jdbcClient.sql("SELECT * FROM TAG WHERE ID = ?")
            .params(id)
            .query(TagRowMapper())
            .optional()

    fun getTag(name: String): Optional<Tag> =
        jdbcClient.sql("SELECT * FROM TAG WHERE name = ?")
            .params(name)
            .query(TagRowMapper())
            .optional()

    fun createTag(tag: NewTag): Tag? {
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        val mapper = jacksonObjectMapper()
        val update =
            jdbcClient.sql("""
                INSERT INTO TAG(NAME) 
                VALUES (:name)
            """.trimIndent())
                .param("name", tag.name)
                .update(keyHolder)

        if (update == 1) {
            val createdTag = getTag(keyHolder.keys?.get("id") as? Int ?:
                throw RuntimeException("Failed to retrieve generated ID"))

            return if (createdTag.isPresent) {
                createdTag.get()
            } else {
                throw ResponseStatusException(HttpStatusCode.valueOf(404))
            }
        } else {
//            logger.error { "Unable to create tag" }
            throw ResponseStatusException(HttpStatusCode.valueOf(500))
        }
    }

    fun deleteTag(id: Int) {
//        TODO delete tag and then delete all occurrences of it in recipe_tag
//        val update = try {
//            jdbcClient.sql("DELETE FROM RECIPE WHERE ID = ?")
//                .params(id)
//                .update()
//        } catch (e: Exception) {
////            logger.error(e) { "Unable to delete person due to error with the query or connection" }
//            throw ResponseStatusException(HttpStatusCode.valueOf(500))
//        }
//        if (update == 1) {
//            return
//        } else {
////            logger.error { "Could not find recipe with id $id" }
//            throw ResponseStatusException(HttpStatusCode.valueOf(404))
//        }
    }

    fun updateTag(tag: Tag): Tag {
        val mapper = jacksonObjectMapper()
        val update = try {
            jdbcClient.sql("""
                    UPDATE TAG 
                    SET name = :name,
                    WHERE id = :id
                """.trimIndent())
                .param("name", tag.name)
                .update()
        } catch (e: Exception) {
//            logger.error(e) { "Unable to update tag due to error with the query or connection" }
            throw ResponseStatusException(HttpStatusCode.valueOf(500))
        }
        if (update == 1) {
            val tagFromDb = getTag(tag.id)
            if (tagFromDb.isPresent) {
                return tagFromDb.get()
            } else {
//                logger.error { "Could not update tag with id $tag.id" }
                throw ResponseStatusException(HttpStatusCode.valueOf(404))
            }
        } else {
//            logger.error { "Could not update tag with id $tag.id" }
            throw ResponseStatusException(HttpStatusCode.valueOf(404))
        }
    }
}