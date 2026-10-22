package com.PerfectPantry.PerfectPantry

import com.PerfectPantry.PerfectPantry.database.user.UserRepository
import com.PerfectPantry.PerfectPantry.model.NewUser
import com.PerfectPantry.PerfectPantry.model.User
import io.kotest.matchers.ints.shouldBeInRange
import io.kotest.matchers.shouldNotBe
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.data.jdbc.test.autoconfigure.DataJdbcTest
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers


@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers

class UserRepositoryTests {

    companion object {
        @Container
        private val postgreSQLContainer = PostgreSQLContainer<Nothing>("postgres:latest")

        @DynamicPropertySource
        @JvmStatic
        fun registerDynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl)
            registry.add("spring.datasource.username", postgreSQLContainer::getUsername)
            registry.add("spring.datasource.password", postgreSQLContainer::getPassword)
        }
    }

    @Autowired
    private lateinit var testObject: UserRepository

    @Test
    fun `when user is saved, the id is populated`() {
        val actual = testObject.createUser(NewUser("Evan"))
        actual shouldNotBe null
        actual?.id?.shouldBeInRange((1..Int.MAX_VALUE))
    }

}