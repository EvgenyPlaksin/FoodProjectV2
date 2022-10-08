package com.lnight.foodProjectV2.di

import com.lnight.foodProjectV2.domain.repository.local.DbMealsRepository
import com.lnight.foodProjectV2.domain.repository.remote.MealsRepository
import com.lnight.foodProjectV2.domain.use_case.MealsUseCases
import com.lnight.foodProjectV2.domain.use_case.get_favorites.GetFavoritesUseCase
import com.lnight.foodProjectV2.domain.use_case.get_meal.GetRecipeUseCase
import com.lnight.foodProjectV2.domain.use_case.get_meals.GetMealsUseCase
import com.lnight.foodProjectV2.domain.use_case.get_resents.GetResentsUseCase
import com.lnight.foodProjectV2.domain.use_case.insert_favotite.InsertFavoriteUseCase
import com.lnight.foodProjectV2.domain.use_case.insert_resent.InsertResentUseCase
import com.lnight.foodProjectV2.domain.use_case.remove_favorite.RemoveFavoriteUseCase
import com.lnight.foodProjectV2.domain.use_case.remove_resents.RemoveResentsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseWrapperModule {

    @Provides
    @Singleton
    fun provideMealsUseCases(
        mealsRepository: MealsRepository,
        dbMealsRepository: DbMealsRepository
    ): MealsUseCases {
        return MealsUseCases(
            getMealsUseCase = GetMealsUseCase(mealsRepository),
            getRecipeUseCase = GetRecipeUseCase(mealsRepository),
            getFavoritesUseCase = GetFavoritesUseCase(dbMealsRepository),
            getResentsUseCase = GetResentsUseCase(dbMealsRepository),
            insertFavoriteUseCase = InsertFavoriteUseCase(dbMealsRepository),
            insertResentUseCase = InsertResentUseCase(dbMealsRepository),
            removeFavoriteUseCase = RemoveFavoriteUseCase(dbMealsRepository),
            removeResentsUseCase = RemoveResentsUseCase(dbMealsRepository)
        )
    }

}