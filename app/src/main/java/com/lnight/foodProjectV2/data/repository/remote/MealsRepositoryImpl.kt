package com.lnight.foodProjectV2.data.repository.remote

import com.lnight.foodProjectV2.data.remote.MealApi
import com.lnight.foodProjectV2.data.remote.dto.DetailMeal
import com.lnight.foodProjectV2.data.remote.dto.MealDto
import com.lnight.foodProjectV2.domain.repository.remote.MealsRepository
import javax.inject.Inject

class MealsRepositoryImpl @Inject constructor(
    private val api: MealApi
): MealsRepository {
    override suspend fun getMeals(query: String): MealDto {
        return api.getMeals(query)
    }

    override suspend fun getRecipe(rId: Int): DetailMeal {
        return api.getRecipes(rId)
    }


}