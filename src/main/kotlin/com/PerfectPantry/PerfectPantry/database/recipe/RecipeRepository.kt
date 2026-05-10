package com.PerfectPantry.PerfectPantry.database.recipe

import com.PerfectPantry.PerfectPantry.model.NewRecipe
import com.PerfectPantry.PerfectPantry.model.Recipe
import org.springframework.http.HttpStatusCode
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

import java.util.Optional

@Service
class RecipeRepository(
    private val jdbcClient: JdbcClient
) {

    fun getRecipesFromDb(): List<Recipe> =
        jdbcClient.sql("SELECT * FROM RECIPE")
            .query(RecipeRowMapper())
            .list()

    fun getRecipe(id: Int): Optional<Recipe> =
        jdbcClient.sql("SELECT * FROM RECIPE WHERE ID = ?")
            .params(id)
            .query(RecipeRowMapper())
            .optional()

    fun searchRecipe(search: String): List<Recipe> =
//        jdbcClient.sql("SELECT *, name <-> :search AS distance FROM recipe ORDER BY distance ASC")
        // TODO I want to do something with the distance value rather than just an initial sorting
        jdbcClient.sql("SELECT * FROM recipe ORDER BY name <-> :search ASC")
            .params(search)
            .query(RecipeRowMapper())
            .list()

    fun existingRecipe(name: String, source: String?): Optional<Recipe> {
        return if (source.isNullOrBlank()) {
            jdbcClient.sql("SELECT * FROM recipe WHERE name = :name LIMIT 1")
                .param("name",name)
                .query(RecipeRowMapper())
                .optional()
        } else {
            jdbcClient.sql("SELECT * FROM recipe WHERE name = :name and source = :source LIMIT 1")
                .param("name",name)
                .param("source", source)
                .query(RecipeRowMapper())
                .optional()
        }
    }

    fun createRecipe(recipe: NewRecipe): Recipe {
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        val mapper = jacksonObjectMapper()
        val update =
            jdbcClient.sql("""
                INSERT INTO RECIPE(NAME, INSTRUCTIONS, DESCRIPTION, TIME, YIELD, SOURCE, URL) 
                VALUES (:name, :instructions::jsonb, :description, :time, :yield, :source, :url)
            """.trimIndent())
                .param("name", recipe.name)
                .param("instructions", mapper.writeValueAsString(recipe.instructions))
                .param("description", recipe.description)
                .param("time", recipe.time)
                .param("yield", recipe.yield)
                .param("source", recipe.source)
                .param("url", recipe.url)
                .update(keyHolder)

        if (update == 1) {
            val potentiallyCreatedRecipe = getRecipe(keyHolder.keys?.get("id") as? Int ?:
                throw RuntimeException("Failed to retrieve generated ID"))

            return if (potentiallyCreatedRecipe.isPresent) {
                potentiallyCreatedRecipe.get()
            } else {
                throw ResponseStatusException(HttpStatusCode.valueOf(404))
            }
        } else {
//            logger.error { "Unable to create recipe $recipe" }
            throw ResponseStatusException(HttpStatusCode.valueOf(500))
        }
    }

    fun deleteRecipe(id: Int) {
        val update = try {
            jdbcClient.sql("DELETE FROM RECIPE WHERE ID = ?")
                .params(id)
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

    fun updateRecipe(recipe: Recipe): Recipe {
        val mapper = jacksonObjectMapper()
        val update = try {
            jdbcClient.sql("""
                    UPDATE RECIPE 
                    SET name = :name, 
                        instructions = :instructions, 
                        description = :description, 
                        time = :time, 
                        yield = :yield, 
                        source = :source, 
                        url = :url 
                    WHERE id = :id
                """.trimIndent())
                .param("name", recipe.name)
                .param("instructions", mapper.writeValueAsString(recipe.instructions))
                .param("description", recipe.description)
                .param("time", recipe.time)
                .param("yield", recipe.yield)
                .param("source", recipe.source)
                .param("url", recipe.url)
                .param("ID", recipe.id)
                .update()
        } catch (e: Exception) {
//            logger.error(e) { "Unable to delete person due to error with the query or connection" }
            throw ResponseStatusException(HttpStatusCode.valueOf(500))
        }
        if (update == 1) {
            val personFromDb = getRecipe(recipe.id)
            if (personFromDb.isPresent) {
                return personFromDb.get()
            } else {
//                logger.error { "Could not update person with id $person.id" }
                throw ResponseStatusException(HttpStatusCode.valueOf(404))
            }
        } else {
//            logger.error { "Could not update person with id $person.id" }
            throw ResponseStatusException(HttpStatusCode.valueOf(404))
        }
    }
}