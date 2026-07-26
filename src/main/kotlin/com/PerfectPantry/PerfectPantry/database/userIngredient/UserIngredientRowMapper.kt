package com.PerfectPantry.PerfectPantry.database.userIngredient

import com.PerfectPantry.PerfectPantry.model.UserIngredient
import org.springframework.jdbc.core.RowMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.sql.ResultSet

class UserIngredientRowMapper: RowMapper<UserIngredient> {
    val mapper = jacksonObjectMapper()
    override fun mapRow(rs: ResultSet, rowNum: Int): UserIngredient {
        return UserIngredient(
            rs.getInt("ingredient_id"),
            rs.getInt("usr_id"),
            rs.getString("notes")
        )
    }
}