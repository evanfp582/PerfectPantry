package com.PerfectPantry.PerfectPantry.database.recipeTag

import com.PerfectPantry.PerfectPantry.model.RecipeTag
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class RecipeTagRowMapper: RowMapper<RecipeTag> {
    override fun mapRow(rs: ResultSet, rowNum: Int): RecipeTag {
        return RecipeTag(
            rs.getInt("recipe_id"),
            rs.getInt("tag_id")
        )
    }
}