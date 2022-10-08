package com.lnight.foodProjectV2.domain.repository.local

import com.lnight.foodProjectV2.data.remote.dto.Recipe
import com.lnight.foodProjectV2.domain.model.Resent

interface DbMealsRepository {

    suspend fun getFavourites(): List<Recipe>

    suspend fun getResents(): List<Resent>

    suspend fun insertResent(resent: Resent)

    suspend fun insertFavourite(recipe: Recipe)

    suspend fun removeOldResents()

    suspend fun removeFavorite(recipe: Recipe)

    suspend fun removeResent(resent: Resent)
}