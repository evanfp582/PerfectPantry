package com.PerfectPantry.PerfectPantry.database.tag

import com.PerfectPantry.PerfectPantry.model.Tag
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class TagRowMapper: RowMapper<Tag> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Tag {
        return Tag(
            rs.getInt("id"),
            rs.getString("name"),
        )
    }
}