package com.PerfectPantry.PerfectPantry.database.userRecipe

import com.PerfectPantry.PerfectPantry.model.UserRecipe
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Service

@Service
class UserRecipeRepository(
    private val jdbcClient: JdbcClient
) {

    fun getAllUserRecipes(): List<UserRecipe> =
        jdbcClient.sql("SELECT * FROM usr_recipe")
            .query(UserRecipeRowMapper())
            .list()
}