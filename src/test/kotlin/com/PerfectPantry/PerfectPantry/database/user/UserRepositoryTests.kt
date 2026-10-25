
package com.PerfectPantry.PerfectPantry.database.user

import com.PerfectPantry.PerfectPantry.model.NewUser
import com.PerfectPantry.PerfectPantry.model.User
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.jdbc.Sql
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.*
import kotlin.test.Ignore
import kotlin.test.assertEquals

@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = ["/sql/users.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class UserRepositoryTests {

    companion object {
        @Container
        @ServiceConnection
        @JvmStatic
        val postgres = PostgreSQLContainer<Nothing>("postgres:18")

        @JvmStatic
        fun registerDatabaseProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgres::getJdbcUrl)
            registry.add("spring.datasource.username", postgres::getUsername)
            registry.add("spring.datasource.password", postgres::getPassword)
        }
    }

    @Autowired
    private lateinit var userRepository: UserRepository

    // For Code coverage reasons, I am going to handle each function in order of it appearing in UserRepository
    @Test
    fun `list users`() {
        val users = userRepository.listUsers()
        val expectedUsers = listOf(
            User(1, "Judytm2001"),
            User(2, "spike8pizza"),
            User(3, "ethanfp582")
        )
        assertEquals(expectedUsers, users, "User list of $users did not match expected $expectedUsers")
    }

    @Test
    fun `search user by present username`() {
        val searchResult = userRepository.searchUsersByUsername("ethanfp582")
        val expectedResult = User(3, "ethanfp582")
        assertEquals(expectedResult, searchResult.get(), "Search of 'ethanfp582' resulted in $searchResult, it was expected to be $expectedResult")
    }

    @Test
    fun `search user by absent username`() {
        val searchResult = userRepository.searchUsersByUsername("evil_person_123")
        assertEquals(Optional.empty<User>(), searchResult, "Search of 'evil_person_123' resulted in $searchResult, it was expected to be Optional.empty")
    }

    @Ignore("not implemented yet")
    @Test fun `get user by id`() {
        // TODO
    }

    @Test
    fun `create user`() {
        val user = NewUser("evanfp582")
        val returnedUser = userRepository.createUser(user)
        assertNotNull(returnedUser)
    }

    @Ignore("not implemented yet")
    @Test fun `create user with duplicate username`() {
        // TODO
    }

}
