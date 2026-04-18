package com.PerfectPantry.PerfectPantry.controllers

import com.PerfectPantry.PerfectPantry.database.person.PersonRepository
import com.PerfectPantry.PerfectPantry.model.NewPerson
import com.PerfectPantry.PerfectPantry.model.Person
import com.PerfectPantry.PerfectPantry.model.Persons
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/persons")
class PersonController(
    private val personRepository: PersonRepository
) {

    fun listPersons(): ResponseEntity<Persons> {
        return ResponseEntity.ok(Persons(personRepository.getPersonsFromDb()))
    }

    @GetMapping("/{id}")
    fun getPerson(
        @PathVariable
        id: Int
    ): ResponseEntity<Person> {
        val potentiallyExistingPerson = personRepository.getPerson(id)
        return if (potentiallyExistingPerson.isPresent) {
            ResponseEntity.ok(potentiallyExistingPerson.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createPerson(@RequestBody person: NewPerson): ResponseEntity<Person> {
        val createdPerson = personRepository.createPerson(person)
        return ResponseEntity.ok().body(createdPerson)
    }

    @DeleteMapping("/{id}")
    fun deletePerson(
        @PathVariable id: Int
    ) {
        personRepository.deletePerson(id)
    }

    @PutMapping
    fun updatePerson(
        @RequestBody person: Person
    ): ResponseEntity<Person> = ResponseEntity.ok(personRepository.updatePerson(person))

//    @PatchMapping("/v1/persons/{id}")
//    fun updatePerson(@PathVariable id: Int, @RequestBody updatedFields: Map<String, Any?>): ResponseEntity<Person> {
//        val updatedPerson = dbService.updatePerson(id, updatedFields)
//        return ResponseEntity.ok(updatedPerson)
//    }
}