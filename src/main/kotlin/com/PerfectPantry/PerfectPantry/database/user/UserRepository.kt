package com.PerfectPantry.PerfectPantry.database.user

import com.PerfectPantry.PerfectPantry.database.recipe.RecipeRowMapper
import com.PerfectPantry.PerfectPantry.database.tag.UserRowMapper
import com.PerfectPantry.PerfectPantry.model.NewUser
import com.PerfectPantry.PerfectPantry.model.User
import com.PerfectPantry.PerfectPantry.model.UserRecipe
import com.PerfectPantry.PerfectPantry.model.Users
import org.springframework.http.HttpStatusCode
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.Optional

// NOTE that the table name is usr, not user, because Postgresql is dumb

@Service
class UserRepository(
    private val jdbcClient: JdbcClient
) {
    fun listUsers(): List<User> =
        jdbcClient.sql("SELECT * FROM usr")
            .query(UserRowMapper())
            .list()

    fun searchUsersByUsername(username: String): Optional<User> =
        jdbcClient.sql("SELECT * FROM usr where username = :username")
            .param("username", username)
            .query(UserRowMapper())
            .optional()

    fun getUserById(id: Int): Optional<User> =
        jdbcClient.sql("SELECT * FROM usr where id = :id")
            .param("id", id)
            .query(UserRowMapper())
            .optional()

    fun createUser(newUser: NewUser): User? {
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        val update =
            jdbcClient.sql("""
                INSERT INTO usr(USERNAME) 
                VALUES (:username)
            """.trimIndent())
                .param("username", newUser.username)
                .update(keyHolder)

        if (update == 1) {
            val potentialUser = getUserById(keyHolder.keys?.get("id") as? Int ?:
                throw RuntimeException("Failed to retrieve generated ID"))
            return if (potentialUser.isPresent) {
                potentialUser.get()
            } else {
                throw ResponseStatusException(HttpStatusCode.valueOf(404), "Failed to get newly created User")
            }
        } else {
            throw ResponseStatusException(HttpStatusCode.valueOf(500), "Unable to create User")
        }
    }

}