package com.PerfectPantry.PerfectPantry.model

data class Persons(
    val persons: List<Person>
)

data class Person(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val age: Int
)

data class NewPerson(
    val firstName: String,
    val lastName: String,
    val age: Int
)