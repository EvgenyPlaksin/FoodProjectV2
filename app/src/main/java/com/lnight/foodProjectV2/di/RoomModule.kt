package com.lnight.foodProjectV2.di

import android.app.Application
import androidx.room.Room
import com.lnight.foodProjectV2.data.local.MealDatabase
import com.lnight.foodProjectV2.data.local.RecipeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): MealDatabase {
        return Room.databaseBuilder(
            app,
            MealDatabase::class.java,
            MealDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideMealDao(db: MealDatabase): RecipeDao {
        return db.recipeDao
    }

}