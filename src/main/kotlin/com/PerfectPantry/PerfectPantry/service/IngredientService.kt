package com.PerfectPantry.PerfectPantry.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class IngredientService (

){

    @Transactional
    fun getFullIngredient(
        ingredientId: Int
    ) {
        TODO()
    }

}
