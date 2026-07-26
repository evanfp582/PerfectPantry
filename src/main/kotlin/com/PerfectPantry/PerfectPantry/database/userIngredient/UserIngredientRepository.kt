package com.PerfectPantry.PerfectPantry.database.userIngredient

import com.PerfectPantry.PerfectPantry.model.RecipeIngredient
import com.PerfectPantry.PerfectPantry.model.UserIngredient
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

}