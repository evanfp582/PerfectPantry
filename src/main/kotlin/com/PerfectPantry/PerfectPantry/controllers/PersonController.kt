package com.PerfectPantry.PerfectPantry.controllers

import com.PerfectPantry.PerfectPantry.database.DbService
import com.PerfectPantry.PerfectPantry.model.NewPerson
import com.PerfectPantry.PerfectPantry.model.Person
import com.PerfectPantry.PerfectPantry.model.Persons
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController("/v1")
class PersonController(
    private val dbService: DbService
) {
    @GetMapping("/v1/persons")
    fun listPersons(): ResponseEntity<Persons> {
        return ResponseEntity.ok(Persons(dbService.getPersonsFromDb()))
    }

    @GetMapping("/v1/persons/{id}")
    fun getPerson(
        @PathVariable
        id: Int
    ): ResponseEntity<Person> {
        val potentiallyExistingPerson = dbService.getPerson(id)
        return if (potentiallyExistingPerson.isPresent) {
            ResponseEntity.ok(potentiallyExistingPerson.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/v1/persons")
    fun createPerson(@RequestBody person: NewPerson): ResponseEntity<Person> {
        val createdPerson = dbService.createPerson(person)
        return ResponseEntity.ok().body(createdPerson)
    }

    @DeleteMapping("/v1/persons/{id}")
    fun deletePerson(
        @PathVariable id: Int
    ) {
        dbService.deletePerson(id)
    }

    @PutMapping("/v1/persons")
    fun updatePerson(
        @RequestBody person: Person
    ): ResponseEntity<Person> = ResponseEntity.ok(dbService.updatePerson(person))

//    @PatchMapping("/v1/persons/{id}")
//    fun updatePerson(@PathVariable id: Int, @RequestBody updatedFields: Map<String, Any?>): ResponseEntity<Person> {
//        val updatedPerson = dbService.updatePerson(id, updatedFields)
//        return ResponseEntity.ok(updatedPerson)
//    }
}