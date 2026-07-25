package com.PerfectPantry.PerfectPantry.database.tag

import com.PerfectPantry.PerfectPantry.model.User
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class UserRowMapper: RowMapper<User> {
    override fun mapRow(rs: ResultSet, rowNum: Int): User {
        return User(
            rs.getInt("id"),
            rs.getString("username"),
        )
    }
}