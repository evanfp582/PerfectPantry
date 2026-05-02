package com.PerfectPantry.PerfectPantry.service

import com.PerfectPantry.PerfectPantry.database.ingredient.IngredientRepository
import com.PerfectPantry.PerfectPantry.database.recipe.RecipeRepository
import com.PerfectPantry.PerfectPantry.database.recipeIngredient.RecipeIngredientRepository
import com.PerfectPantry.PerfectPantry.database.recipeTag.RecipeTagRepository
import com.PerfectPantry.PerfectPantry.database.tag.TagRepository
import com.PerfectPantry.PerfectPantry.model.CreateRecipeRequest

import com.PerfectPantry.PerfectPantry.model.Recipe
import com.PerfectPantry.PerfectPantry.model.RecipeIngredient
import com.PerfectPantry.PerfectPantry.model.RecipeTag
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RecipeService (
    private val recipeRepository: RecipeRepository,
    private val ingredientRepository: IngredientRepository,
    private val tagRepository: TagRepository,
    private val recipeIngredientRepository: RecipeIngredientRepository,
    private val recipeTagRepository: RecipeTagRepository
){

    @Transactional
    fun createFullRecipe(
        createRecipeRequest: CreateRecipeRequest
    ): Recipe {
        // First, check if the recipe exists
        // If name and source are equal to something that already exists

        // If not, create the recipe
        val recipe = recipeRepository.createRecipe(createRecipeRequest.recipe)

        //Next, create recipes where needed, but regardless, I am getting IDs and linking
        createRecipeRequest.ingredients.forEach { recipeIngredientInput ->
            val ingredient = ingredientRepository.getIngredientByName(recipeIngredientInput.ingredient.name)
                .orElse(ingredientRepository.createIngredient(recipeIngredientInput.ingredient))
//            recipeIngredientRepository.getRecipeIngredient(ingredient.id, recipe.id)
            recipeIngredientRepository.createRecipeIngredientById(
                RecipeIngredient(
                    ingredient.id,
                    recipe.id,
                    recipeIngredientInput.quantity,
                    recipeIngredientInput.unit
                )
            )

        }

        //Next, create tags where needed, but regardless, I am getting IDs and linking
        createRecipeRequest.tags.forEach { tagInput ->
            val tag = tagRepository.getTag(tagInput)
                .orElse(tagRepository.createTag(tagInput))
            recipeTagRepository.createRecipeTag(RecipeTag(recipe.id, tag.id))

        }

        return recipe

    }

    @Transactional
    fun deleteFullRecipe(
        recipeId: Int
    ) {
        recipeIngredientRepository.deleteRecipeIngredientByRecipe(recipeId)
        recipeTagRepository.deleteRecipeTagByRecipe(recipeId)
        recipeRepository.deleteRecipe(recipeId)
    }

}
