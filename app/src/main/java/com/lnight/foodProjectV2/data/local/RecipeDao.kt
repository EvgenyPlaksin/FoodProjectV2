package com.lnight.foodProjectV2.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lnight.foodProjectV2.data.remote.dto.Recipe
import com.lnight.foodProjectV2.domain.model.Resent

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipe")
    fun getFavourites(): List<Recipe>

    @Query("SELECT * FROM resent")
     fun getResents(): List<Resent>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResent(resent: Resent)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(recipe: Recipe)

    @Query("DELETE FROM resent WHERE id IN (SELECT id FROM resent ORDER BY timeStamp DESC LIMIT 1 OFFSET 20)")
    suspend fun removeOldResents()

    @Delete(entity = Recipe::class)
    suspend fun removeFavorite(recipe: Recipe)

    @Delete(entity = Resent::class)
    suspend fun removeResent(resent: Resent)
}