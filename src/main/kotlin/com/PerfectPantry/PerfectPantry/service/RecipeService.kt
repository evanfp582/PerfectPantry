package com.PerfectPantry.PerfectPantry.service

import com.PerfectPantry.PerfectPantry.database.ingredient.IngredientRepository
import com.PerfectPantry.PerfectPantry.database.recipe.RecipeRepository
import com.PerfectPantry.PerfectPantry.database.recipeIngredient.RecipeIngredientRepository
import com.PerfectPantry.PerfectPantry.database.recipeTag.RecipeTagRepository
import com.PerfectPantry.PerfectPantry.database.tag.TagRepository
import com.PerfectPantry.PerfectPantry.model.NewIngredient
import com.PerfectPantry.PerfectPantry.model.NewRecipe
import com.PerfectPantry.PerfectPantry.model.NewTag
import com.PerfectPantry.PerfectPantry.model.Recipe
import com.PerfectPantry.PerfectPantry.model.RecipeTag
import com.PerfectPantry.PerfectPantry.model.Tag
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
        newRecipe: NewRecipe,
        ingredients: List<NewIngredient>,
        tags: List<NewTag>
    ): Recipe {
        // First, check if the recipe exists
        // If name and source are equal to something that already exists

        // If not, create the recipe
        val recipe = recipeRepository.createRecipe(newRecipe)

        //Next, create recipes where needed, but regardless, I am getting IDs and linking
        ingredients.forEach { ingredientInput ->
            val ingredient = ingredientRepository.getIngredientByName(ingredientInput.name)
                .orElse(ingredientRepository.createIngredient(ingredientInput))
            recipeIngredientRepository.getRecipeIngredient(ingredient.id, recipe.id)

        }

        //Next, create tags where needed, but regardless, I am getting IDs and linking
        tags.forEach { tagInput ->
            val tag = tagRepository.getTag(tagInput.name)
                .orElse(tagRepository.createTag(tagInput))
            recipeTagRepository.createRecipeTag(RecipeTag(recipe.id, tag.id))

        }

        return recipe

    }

}
