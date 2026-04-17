package com.PerfectPantry.PerfectPantry.database

import com.PerfectPantry.PerfectPantry.model.NewPerson
import com.PerfectPantry.PerfectPantry.model.Person
import org.springframework.http.HttpStatusCode
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class DbService(
    private val jdbcClient: JdbcClient
) {
    fun getPersonsFromDb(): List<Person> =
        jdbcClient.sql("SELECT * FROM PERSON")
            .query(PersonRowMapper())
            .list()

    fun getPerson(id: Int): java.util.Optional<Person> =
        jdbcClient.sql("SELECT * FROM PERSON WHERE ID = ?")
            .params(id)
            .query(PersonRowMapper())
            .optional()

    fun createPerson(person: NewPerson): Person? {
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        val update =
            jdbcClient.sql("INSERT INTO PERSON(FIRST_NAME, LAST_NAME, AGE) VALUES (?, ?, ?)")
                .params(person.firstName, person.lastName, person.age)
                .update(keyHolder)

        if (update == 1) {
            val potentiallyCreatedPerson = getPerson(keyHolder.getKeyAs(Integer::class.java)?.toInt()!!)

            return if (potentiallyCreatedPerson.isPresent) {
                potentiallyCreatedPerson.get()
            } else {
                throw ResponseStatusException(HttpStatusCode.valueOf(404))
            }
        } else {
//            logger.error { "Unable to create person $person" }
            throw ResponseStatusException(HttpStatusCode.valueOf(500))
        }
    }

    fun deletePerson(id: Int) {
        val update = try {
            jdbcClient.sql("DELETE FROM PERSON WHERE ID = ?")
                .params(id)
                .update()
        } catch (e: Exception) {
//            logger.error(e) { "Unable to delete person due to error with the query or connection" }
            throw ResponseStatusException(HttpStatusCode.valueOf(500))
        }
        if (update == 1) {
            return
        } else {
//            logger.error { "Could not find person with id $id" }
            throw ResponseStatusException(HttpStatusCode.valueOf(404))
        }
    }

    fun updatePerson(person: Person): Person {
        val update = try {
            jdbcClient.sql("UPDATE PERSON SET FIRST_NAME = ?, LAST_NAME = ?, AGE = ? WHERE ID = ?")
                .params(person.firstName, person.lastName, person.age, person.id)
                .update()
        } catch (e: Exception) {
//            logger.error(e) { "Unable to delete person due to error with the query or connection" }
            throw ResponseStatusException(HttpStatusCode.valueOf(500))
        }
        if (update == 1) {
            val personFromDb = getPerson(person.id)
            if (personFromDb.isPresent) {
                return personFromDb.get()
            } else {
//                logger.error { "Could not update person with id $person.id" }
                throw ResponseStatusException(HttpStatusCode.valueOf(404))
            }
        } else {
//            logger.error { "Could not update person with id $person.id" }
            throw ResponseStatusException(HttpStatusCode.valueOf(404))
        }
    }
}