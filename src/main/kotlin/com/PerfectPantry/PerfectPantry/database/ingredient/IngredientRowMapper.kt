package com.PerfectPantry.PerfectPantry.database.ingredient

import com.PerfectPantry.PerfectPantry.model.Ingredient
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class IngredientRowMapper: RowMapper<Ingredient> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Ingredient {
        return Ingredient(
            rs.getInt("ID"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getFloat("cost")
        )
    }
}