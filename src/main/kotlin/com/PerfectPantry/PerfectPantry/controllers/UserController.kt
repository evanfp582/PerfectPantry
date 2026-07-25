package com.PerfectPantry.PerfectPantry.controllers

import com.PerfectPantry.PerfectPantry.database.user.UserRepository
import com.PerfectPantry.PerfectPantry.model.User
import com.PerfectPantry.PerfectPantry.model.Users
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.jvm.optionals.getOrElse

@RestController
@RequestMapping("/v1/user")
class UserController(
    private val userRepository: UserRepository,
) {
    @GetMapping("/all")
    fun listUsers(): ResponseEntity<Users> =
        ResponseEntity.ok(Users(userRepository.listUsers()))

    @GetMapping("/username/{username}")
    fun searchUser(@PathVariable username: String): ResponseEntity<User> =
        ResponseEntity.ok(userRepository.searchUsersByUsername(username).getOrElse { error("Username $username not found\n") })

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Int): ResponseEntity<User> =
        ResponseEntity.ok(userRepository.getUserById(id).get())

}