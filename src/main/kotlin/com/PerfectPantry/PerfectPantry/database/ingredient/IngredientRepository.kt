package com.PerfectPantry.PerfectPantry.database.ingredient

import com.PerfectPantry.PerfectPantry.model.Ingredient
import com.PerfectPantry.PerfectPantry.model.NewIngredient
import org.springframework.http.HttpStatusCode
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

import java.util.Optional

@Service
class IngredientRepository(
    private val jdbcClient: JdbcClient
) {

    fun getIngredientsFromDb(): List<Ingredient> =
        jdbcClient.sql("SELECT * FROM INGREDIENT")
            .query(IngredientRowMapper())
            .list()

    fun getIngredient(id: Int): Optional<Ingredient> =
        jdbcClient.sql("SELECT * FROM INGREDIENT WHERE ID = ?")
            .params(id)
            .query(IngredientRowMapper())
            .optional()

    fun getIngredientByName(name: String): Optional<Ingredient> =
        jdbcClient.sql("SELECT * FROM INGREDIENT WHERE NAME = ?")
            .params(name)
            .query(IngredientRowMapper())
            .optional()

    fun createIngredient(ingredient: NewIngredient): Ingredient {
        // TODO Name should be unique
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        val update =
            jdbcClient.sql("""
                INSERT INTO ingredient (name, description, cost)
                VALUES (:name, :description, :cost)
            """.trimIndent())
                .param("name", ingredient.name)
                .param("description", ingredient.description)
                .param("cost", ingredient.cost)
                .update(keyHolder)

        if (update == 1) {
            val potentialIngredient = getIngredient(keyHolder.keys?.get("id") as? Int ?:
                throw RuntimeException("Failed to retrieve generated ID"))

            return if (potentialIngredient.isPresent) {
                potentialIngredient.get()
            } else {
                throw ResponseStatusException(HttpStatusCode.valueOf(404))
            }
        } else {
//            logger.error { "Unable to create ingredient $ingredient" }
            throw ResponseStatusException(HttpStatusCode.valueOf(500))
        }
    }

    fun deleteIngredient(id: Int) {
        val update = try {
            jdbcClient.sql("DELETE FROM INGREDIENT WHERE ID = ?")
                .params(id)
                .update()
        } catch (e: Exception) {
//            logger.error(e) { "Unable to delete ingredient due to error with the query or connection" }
            throw ResponseStatusException(HttpStatusCode.valueOf(500))
        }
        if (update == 1) {
            return
        } else {
//            logger.error { "Could not find ingredient with id $id" }
            throw ResponseStatusException(HttpStatusCode.valueOf(404))
        }
    }

    fun updateIngredient(ingredient: Ingredient): Ingredient {
        val update = try {
            jdbcClient.sql("""
                UPDATE INGREDIENT
                SET name = :name,
                    description = :description,
                    cost = :cost
                WHERE id = :id
            """.trimIndent())
                .param("name", ingredient.name)
                .param("description", ingredient.description)
                .param("cost", ingredient.cost)
                .param("id", ingredient.id)
                .update()
        } catch (e: Exception) {
//            logger.error(e) { "Unable to delete ingredient due to error with the query or connection" }
            throw ResponseStatusException(HttpStatusCode.valueOf(500))
        }
        if (update == 1) {
            val personFromDb = getIngredient(ingredient.id)
            if (personFromDb.isPresent) {
                return personFromDb.get()
            } else {
//                logger.error { "Could not update ingredient with id $person.id" }
                throw ResponseStatusException(HttpStatusCode.valueOf(404))
            }
        } else {
//            logger.error { "Could not update ingredient with id $person.id" }
            throw ResponseStatusException(HttpStatusCode.valueOf(404))
        }
    }
}