package com.PerfectPantry.PerfectPantry.model

import com.PerfectPantry.PerfectPantry.database.types.Measurements

data class RecipeIngredient (
    val ingredientId: Int,
    val recipeId: Int,
    val quantity: Double,
    val unit: Measurements
)

data class NewRecipeIngredient (
    val ingredient: NewIngredient,
    val quantity: Double,
    val unit: Measurements
)