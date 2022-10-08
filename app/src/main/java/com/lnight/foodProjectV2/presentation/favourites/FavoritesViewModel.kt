package com.lnight.foodProjectV2.presentation.favourites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lnight.foodProjectV2.common.Resource
import com.lnight.foodProjectV2.data.remote.dto.Recipe
import com.lnight.foodProjectV2.domain.use_case.MealsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val mealsUseCases: MealsUseCases
): ViewModel() {

    private val _getFavouritesResult = MutableSharedFlow<Resource<List<Recipe>>>()
    val getFavoritesResult = _getFavouritesResult.asSharedFlow()

    fun getFavorites() {
        Log.e("TAG", "getFavorites")
        viewModelScope.launch {
            mealsUseCases.getFavoritesUseCase().collect {
                _getFavouritesResult.emit(it)
            }
        }
    }

    fun removeItem(recipe: Recipe) {
        viewModelScope.launch {
            mealsUseCases.removeFavoriteUseCase(recipe)
        }
    }

}