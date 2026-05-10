package com.PerfectPantry.PerfectPantry.database.recipeIngredient

import com.PerfectPantry.PerfectPantry.database.types.Measurements
import com.PerfectPantry.PerfectPantry.model.RecipeIngredient
import org.springframework.jdbc.core.RowMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.sql.ResultSet

class RecipeIngredientRowMapper: RowMapper<RecipeIngredient> {
    val mapper = jacksonObjectMapper()
    override fun mapRow(rs: ResultSet, rowNum: Int): RecipeIngredient {
        return RecipeIngredient(
            rs.getInt("recipe_id"),
            rs.getInt("ingredient_id"),
            rs.getDouble("quantity"),
            Measurements.valueOf(rs.getString("unit").uppercase())
        )
    }
}