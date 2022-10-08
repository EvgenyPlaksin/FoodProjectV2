package com.lnight.foodProjectV2.domain.use_case.remove_favorite

import android.util.Log
import com.lnight.foodProjectV2.data.remote.dto.Recipe
import com.lnight.foodProjectV2.domain.repository.local.DbMealsRepository

class RemoveFavoriteUseCase(
    private val repository: DbMealsRepository
) {
    suspend operator fun invoke(recipe: Recipe) {
        try {
            repository.removeFavorite(recipe)
        } catch (e: Exception) {
            Log.e("TAG", "e -> ${e.localizedMessage}")
        }
    }
}