package com.lnight.foodProjectV2.data.remote.dto

import com.lnight.foodProjectV2.domain.model.Meal

data class MealDto(
    val count: Int,
    val recipes: List<Recipe>
)
 fun MealDto.toMeal(): Meal {
    return Meal(
        recipes = recipes
    )
}