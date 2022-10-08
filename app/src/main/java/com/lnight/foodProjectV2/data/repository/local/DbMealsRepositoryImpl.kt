package com.lnight.foodProjectV2.data.repository.local

import com.lnight.foodProjectV2.data.local.RecipeDao
import com.lnight.foodProjectV2.data.remote.dto.Recipe
import com.lnight.foodProjectV2.domain.model.Resent
import com.lnight.foodProjectV2.domain.repository.local.DbMealsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DbMealsRepositoryImpl @Inject constructor(
    private val dao: RecipeDao
): DbMealsRepository {
    override suspend fun getFavourites(): List<Recipe> {
        return withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            dao.getFavourites()
        }
    }

    override suspend fun getResents(): List<Resent> {
        return withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            dao.getResents()
        }
    }

    override suspend fun insertResent(resent: Resent) {
        dao.insertResent(resent)
    }

    override suspend fun insertFavourite(recipe: Recipe) {
        dao.insertFavorite(recipe)
    }

    override suspend fun removeOldResents() {
        dao.removeOldResents()
    }

    override suspend fun removeFavorite(recipe: Recipe) {
        dao.removeFavorite(recipe)
    }

    override suspend fun removeResent(resent: Resent) {
        dao.removeResent(resent)
    }
}