package com.lnight.foodProjectV2.domain.repository.remote

import com.lnight.foodProjectV2.data.remote.dto.MealDto
import com.lnight.foodProjectV2.data.remote.dto.DetailMeal

interface MealsRepository {
    suspend fun getMeals (query: String) : MealDto
    suspend fun getRecipe (rId : Int) : DetailMeal
}