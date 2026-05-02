package com.PerfectPantry.PerfectPantry.controllers

import com.PerfectPantry.PerfectPantry.database.recipe.RecipeRepository
import com.PerfectPantry.PerfectPantry.model.CreateRecipeRequest
import com.PerfectPantry.PerfectPantry.model.NewRecipe
import com.PerfectPantry.PerfectPantry.model.Recipe
import com.PerfectPantry.PerfectPantry.model.Recipes
import com.PerfectPantry.PerfectPantry.service.RecipeService
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
@RequestMapping("/v1/recipe")
class RecipeController(
    private val recipeRepository: RecipeRepository,
    private val recipeService: RecipeService
) {
    @GetMapping("/all")
    fun listRecipes(): ResponseEntity<Recipes> =
        ResponseEntity.ok(Recipes(recipeRepository.getRecipesFromDb()))

    @GetMapping("/{id}")
    fun getRecipe(@PathVariable id: Int): ResponseEntity<Recipe> {
        val potentialRecipe = recipeRepository.getRecipe(id)
        return if (potentialRecipe.isPresent) {ResponseEntity.ok(potentialRecipe.get())}
        else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/only")
    fun createRecipe(@RequestBody recipe: NewRecipe): ResponseEntity<Recipe> {
        val createdRecipe = recipeRepository.createRecipe(recipe)
        return ResponseEntity.ok(createdRecipe)
    }

    @PostMapping
    fun createFullRecipe(
        @RequestBody request: CreateRecipeRequest,
    ): ResponseEntity<Recipe> {
        val createdRecipe = recipeService.createFullRecipe(request)
        return ResponseEntity.ok(createdRecipe)
    }

    @DeleteMapping("/{id}")
    fun deleteRecipe(@PathVariable id: Int) {
        print("Deleting Recipe")
        recipeService.deleteFullRecipe(id)
    }

    @PutMapping
    fun updateRecipe(@RequestBody recipe: Recipe): ResponseEntity<Recipe> =
        ResponseEntity.ok(recipeRepository.updateRecipe(recipe))


}