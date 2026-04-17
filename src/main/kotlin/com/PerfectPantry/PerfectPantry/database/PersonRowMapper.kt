package com.PerfectPantry.PerfectPantry.database

import com.PerfectPantry.PerfectPantry.model.Person
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class PersonRowMapper: RowMapper<Person> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Person {
        return Person(
            rs.getInt("ID"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getInt("ID")
        )
    }
}