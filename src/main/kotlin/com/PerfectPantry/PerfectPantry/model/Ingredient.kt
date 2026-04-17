package com.PerfectPantry.PerfectPantry.model

data class Ingredients(
    val ingredients: List<Ingredient>
)

data class Ingredient(
    val id: Int,
    val name: String,
    val description: String,
    val cost: Float
)

data class NewIngredient(
    val name: String,
    val description: String,
    val cost: Float
)