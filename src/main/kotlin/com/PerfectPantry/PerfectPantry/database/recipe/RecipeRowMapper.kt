package com.PerfectPantry.PerfectPantry.database.recipe

import com.PerfectPantry.PerfectPantry.model.Person
import com.PerfectPantry.PerfectPantry.model.Recipe
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class RecipeRowMapper: RowMapper<Recipe> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Recipe {
        return Recipe(
            rs.getInt("ID"),
            rs.getString("name"),
            rs.getString("instructions"),
            rs.getString("description"),
            rs.getString("time"),
            rs.getInt("yield"),
            rs.getString("source"),
            rs.getString("url")
        )
    }
}