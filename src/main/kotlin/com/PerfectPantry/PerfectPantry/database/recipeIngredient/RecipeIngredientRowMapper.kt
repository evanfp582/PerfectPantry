package com.PerfectPantry.PerfectPantry.database.recipeIngredient

import com.PerfectPantry.PerfectPantry.model.Recipe
import com.PerfectPantry.PerfectPantry.model.RecipeIngredient
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class RecipeIngredientRowMapper: RowMapper<RecipeIngredient> {
    override fun mapRow(rs: ResultSet, rowNum: Int): RecipeIngredient {
        return RecipeIngredient(
            rs.getInt("recipeId"),
            rs.getInt("ingredientId"),
            rs.getDouble("quantity"),
        )
    }
}