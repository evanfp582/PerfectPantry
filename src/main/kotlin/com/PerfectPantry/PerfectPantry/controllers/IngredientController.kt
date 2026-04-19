package com.PerfectPantry.PerfectPantry.controllers

import com.PerfectPantry.PerfectPantry.database.ingredient.IngredientRepository
import com.PerfectPantry.PerfectPantry.model.Ingredient
import com.PerfectPantry.PerfectPantry.model.Ingredients
import com.PerfectPantry.PerfectPantry.model.NewIngredient
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
@RequestMapping("/v1/ingredient")
class IngredientController(
    private val ingredientRepository: IngredientRepository
) {

    @GetMapping("/all")
    fun listIngredients(): ResponseEntity<Ingredients> =
        ResponseEntity.ok(Ingredients(ingredientRepository.getIngredientsFromDb()))

    @GetMapping("/{id}")
    fun getIngredient(@PathVariable id: Int): ResponseEntity<Ingredient> {
        val potentialIngredient = ingredientRepository.getIngredient(id)
        return if (potentialIngredient.isPresent) {ResponseEntity.ok(potentialIngredient.get())}
        else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createIngredient(@RequestBody ingredient: NewIngredient): ResponseEntity<Ingredient> {
        val createdIngredient = ingredientRepository.createIngredient(ingredient)
        return ResponseEntity.ok(createdIngredient)
    }

    @DeleteMapping("/{id}")
    fun deleteIngredient(@PathVariable id: Int) {
        ingredientRepository.deleteIngredient(id)
    }

    @PutMapping
    fun updateIngredient(@RequestBody ingredient: Ingredient): ResponseEntity<Ingredient> =
        ResponseEntity.ok(ingredientRepository.updateIngredient(ingredient))


}