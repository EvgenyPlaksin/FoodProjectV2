package com.lnight.foodProjectV2.domain.use_case

import com.lnight.foodProjectV2.domain.use_case.get_favorites.GetFavoritesUseCase
import com.lnight.foodProjectV2.domain.use_case.get_meal.GetRecipeUseCase
import com.lnight.foodProjectV2.domain.use_case.get_meals.GetMealsUseCase
import com.lnight.foodProjectV2.domain.use_case.get_resents.GetResentsUseCase
import com.lnight.foodProjectV2.domain.use_case.insert_favotite.InsertFavoriteUseCase
import com.lnight.foodProjectV2.domain.use_case.insert_resent.InsertResentUseCase
import com.lnight.foodProjectV2.domain.use_case.remove_favorite.RemoveFavoriteUseCase
import com.lnight.foodProjectV2.domain.use_case.remove_resents.RemoveResentsUseCase

data class MealsUseCases(
    val getMealsUseCase: GetMealsUseCase,
    val getRecipeUseCase: GetRecipeUseCase,
    val getResentsUseCase: GetResentsUseCase,
    val getFavoritesUseCase: GetFavoritesUseCase,
    val insertResentUseCase: InsertResentUseCase,
    val insertFavoriteUseCase: InsertFavoriteUseCase,
    val removeResentsUseCase: RemoveResentsUseCase,
    val removeFavoriteUseCase: RemoveFavoriteUseCase
)
