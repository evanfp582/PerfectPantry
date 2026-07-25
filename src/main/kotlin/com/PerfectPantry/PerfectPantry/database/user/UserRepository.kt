package com.PerfectPantry.PerfectPantry.database.user

import com.PerfectPantry.PerfectPantry.database.recipe.RecipeRowMapper
import com.PerfectPantry.PerfectPantry.database.tag.UserRowMapper
import com.PerfectPantry.PerfectPantry.model.User
import com.PerfectPantry.PerfectPantry.model.Users
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Service
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
}