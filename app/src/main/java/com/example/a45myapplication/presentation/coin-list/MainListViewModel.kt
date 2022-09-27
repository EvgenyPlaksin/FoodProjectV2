package com.example.a45myapplication.presentation.`coin-list`

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a45myapplication.common.Resourcce
import com.example.a45myapplication.domain.get_meals.GetMealsUseCase
import com.example.a45myapplication.domain.model.Meal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainListViewModel @Inject constructor(
private val getMealsUseCase: GetMealsUseCase

) : ViewModel(){
    private val _getMealsResult = MutableSharedFlow<Resourcce<Meal>>()
    val getMealsResult = _getMealsResult.asSharedFlow()
    fun getMeals(query: String) {
        viewModelScope.launch {
            getMealsUseCase(query).collect {
                _getMealsResult.emit(it)
            }
        }
    }
}
