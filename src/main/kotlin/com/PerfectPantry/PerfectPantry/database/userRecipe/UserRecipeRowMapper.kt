package com.PerfectPantry.PerfectPantry.database.userRecipe

import com.PerfectPantry.PerfectPantry.model.UserRecipe
import org.springframework.jdbc.core.RowMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.sql.ResultSet

class UserRecipeRowMapper: RowMapper<UserRecipe> {
    val mapper = jacksonObjectMapper()
    override fun mapRow(rs: ResultSet, rowNum: Int): UserRecipe {
        return UserRecipe(
            rs.getInt("recipe_id"),
            rs.getInt("usr_id"),
            rs.getString("notes")
        )
    }
}