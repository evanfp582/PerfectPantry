
package com.PerfectPantry.PerfectPantry.database.user

import com.PerfectPantry.PerfectPantry.model.NewUser
import com.PerfectPantry.PerfectPantry.model.User
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.jdbc.Sql
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import kotlin.test.assertEquals

@SpringBootTest
@Sql(scripts = ["/sql/users.sql"])
@Testcontainers
class UserRepositoryTests {

    companion object {
        @Container
        @JvmStatic
        val postgres = PostgreSQLContainer<Nothing>("postgres:18")

        @DynamicPropertySource
        @JvmStatic
        fun registerDatabaseProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgres::getJdbcUrl)
            registry.add("spring.datasource.username", postgres::getUsername)
            registry.add("spring.datasource.password", postgres::getPassword)
        }
    }

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun `create user`() {
        val user = NewUser("evanfp582")

        val returnedUser = userRepository.createUser(user)

        assertNotNull(returnedUser)
        print(returnedUser)
    }

    @Test
    fun `list users`() {

        val users = userRepository.listUsers()
        print(users)
        val expectedUsers = listOf(
            User(1, "Judytm2001"),
            User(2, "spike8pizza"),
            User(3, "ethanfp582")
        )
        assertEquals(expectedUsers, users, "User list of $users did not match expected $expectedUsers")

    }
}
