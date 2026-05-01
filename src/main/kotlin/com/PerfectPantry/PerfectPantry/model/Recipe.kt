package com.PerfectPantry.PerfectPantry.model

import kotlinx.serialization.json.Json

data class Recipes(
    val recipes: List<Recipe>
)

data class Recipe(
    val id: Int,
    val name: String,
    val instructions: Instructions,
    val description: String?,
    val time: String?,
    val yield: Double?,
    val source: String?,
    val url: String?
)

data class NewRecipe(
    val name: String,
    val instructions: Instructions,
    val description: String?,
    val time: String?,
    val yield: Double?,
    val source: String?,
    val url: String?
)

data class Instructions(
    val steps: List<String>
)

data class CreateRecipeRequest(
    val recipe: NewRecipe,
    val ingredients: List<NewRecipeIngredient>,
    val tags: List<String>
)