package com.PerfectPantry.PerfectPantry.model

data class Recipes(
    val recipes: List<Recipe>
)

data class Recipe(
    val id: Int,
    val name: String,
    val instruction: String,
    val description: String,
    val time: String,
    val yield: Int,
    val source: String,
    val url: String
)

data class NewRecipe(
    val name: String,
    val instruction: String,
    val description: String,
    val time: String,
    val yield: Int,
    val source: String,
    val url: String
)