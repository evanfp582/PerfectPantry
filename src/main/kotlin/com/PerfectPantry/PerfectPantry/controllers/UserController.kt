package com.PerfectPantry.PerfectPantry.controllers

import com.PerfectPantry.PerfectPantry.database.user.UserRepository
import com.PerfectPantry.PerfectPantry.database.userIngredient.UserIngredientRepository
import com.PerfectPantry.PerfectPantry.database.userRecipe.UserRecipeRepository
import com.PerfectPantry.PerfectPantry.model.NewUser
import com.PerfectPantry.PerfectPantry.model.User
import com.PerfectPantry.PerfectPantry.model.UserIngredient
import com.PerfectPantry.PerfectPantry.model.UserRecipe
import com.PerfectPantry.PerfectPantry.model.Users
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.jvm.optionals.getOrElse

@RestController
@RequestMapping("/v1/user")
class UserController(
    private val userRepository: UserRepository,
    private val userRecipeRepository: UserRecipeRepository,
    private val userIngredientRepository: UserIngredientRepository
) {
    @GetMapping("/all")
    fun listUsers(): ResponseEntity<Users> =
        ResponseEntity.ok(Users(userRepository.listUsers()))

    @GetMapping("/username/{username}")
    fun searchUser(@PathVariable username: String): ResponseEntity<User> =
        ResponseEntity.ok(userRepository.searchUsersByUsername(username).getOrElse { error("Username $username not found\n") })

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Int): ResponseEntity<User> =
        ResponseEntity.ok(userRepository.getUserById(id).get())

    @PostMapping("/create/user")
    fun createUser(@RequestBody newUser: NewUser): ResponseEntity<User> {
        val createdUser = userRepository.createUser(newUser)
        return ResponseEntity.ok(createdUser)
    }

    @PostMapping("/create/userRecipe")
    fun createUserRecipe(@RequestBody userRecipe: UserRecipe): ResponseEntity<UserRecipe> {
        val createdUserRecipe = userRecipeRepository.createUserRecipe(userRecipe)
        return ResponseEntity.ok(createdUserRecipe)
    }

    @PostMapping("/create/userIngredient")
    fun createUserIngredient(@RequestBody userIngredient: UserIngredient): ResponseEntity<UserIngredient> {
        val createdUserIngredient = userIngredientRepository.createUserIngredient(userIngredient)
        return ResponseEntity.ok(createdUserIngredient)
    }

//
//    @GetMapping("/recipes/all")
//    fun  getAllUserRecipes(): ResponseEntity<List<UserRecipe>> =
//        ResponseEntity.ok(userRecipeRepository.getAllUserRecipes())

    @GetMapping("/recipe/{userId}")
    fun  getAllUserRecipesByUser(@PathVariable userId: Int): ResponseEntity<List<UserRecipe>> =
        ResponseEntity.ok(userRecipeRepository.getUserRecipeByUser(userId))

    @GetMapping("/recipe_id/{recipeId}")
    fun  getAllUserRecipesByRecipe(@PathVariable recipeId: Int): ResponseEntity<List<UserRecipe>> =
        ResponseEntity.ok(userRecipeRepository.getUserRecipeByRecipe(recipeId))

//    @GetMapping("/all")
//    fun  getAllUserIngredients(): ResponseEntity<List<UserIngredient>> =
//        ResponseEntity.ok(userIngredientRepository.getAllUserIngredients())

    @GetMapping("/ingredient/{userId}")
    fun  getAllUserIngredientsByUser(@PathVariable userId: Int): ResponseEntity<List<UserIngredient>> =
        ResponseEntity.ok(userIngredientRepository.getUserIngredientsByUser(userId))

    @GetMapping("/ingredient_id/{ingredientId}")
    fun  getAllUserIngredientsByIngredient(@PathVariable ingredientId: Int): ResponseEntity<List<UserIngredient>> =
        ResponseEntity.ok(userIngredientRepository.getUserIngredientsByIngredient(ingredientId))

}