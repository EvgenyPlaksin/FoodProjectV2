package com.lnight.foodProjectV2.domain.use_case.insert_favotite

import com.lnight.foodProjectV2.data.remote.dto.Recipe
import com.lnight.foodProjectV2.domain.repository.local.DbMealsRepository

class InsertFavoriteUseCase(
    private val repository: DbMealsRepository
) {
    suspend operator fun invoke(recipe: Recipe) {
        repository.insertFavourite(recipe)
    }
}