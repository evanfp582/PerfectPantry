package com.PerfectPantry.PerfectPantry.model

data class User(
    val id: Int,
    val username: String
)

data class NewUser(
    val username: String
)

data class Users(
    val users: List<User>
)