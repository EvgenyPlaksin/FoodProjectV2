package com.lnight.foodProjectV2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lnight.foodProjectV2.data.remote.dto.Recipe
import com.lnight.foodProjectV2.domain.model.Resent

@Database(
    entities = [Recipe::class, Resent::class],
    version = 3,
    exportSchema = true,
)
abstract class MealDatabase: RoomDatabase() {

    abstract val recipeDao: RecipeDao

        companion object {
            const val DATABASE_NAME = "meals_db"
        }
}