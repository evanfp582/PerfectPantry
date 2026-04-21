package com.PerfectPantry.PerfectPantry.database.recipe

import com.PerfectPantry.PerfectPantry.model.Instructions
import com.fasterxml.jackson.databind.ObjectMapper
import com.PerfectPantry.PerfectPantry.model.Recipe
import kotlinx.serialization.json.Json
import org.springframework.jdbc.core.RowMapper
import tools.jackson.module.kotlin.jacksonObjectMapper
import java.sql.ResultSet

class RecipeRowMapper: RowMapper<Recipe> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Recipe {
        val mapper = jacksonObjectMapper()
        return Recipe(
            rs.getInt("ID"),
            rs.getString("name"),
            mapper.readValue(rs.getString("instructions"), Instructions::class.java),
            rs.getString("description"),
            rs.getString("time"),
            rs.getDouble("yield"),
            rs.getString("source"),
            rs.getString("url")
        )
    }
}