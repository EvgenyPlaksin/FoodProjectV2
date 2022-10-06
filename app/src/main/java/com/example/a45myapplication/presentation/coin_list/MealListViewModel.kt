package com.example.a45myapplication.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a45myapplication.common.Resource
import com.example.a45myapplication.domain.get_meals.GetMealsUseCase
import com.example.a45myapplication.domain.model.Meal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealListViewModel @Inject constructor(
private val getMealsUseCase: GetMealsUseCase

): ViewModel() {

    private val _getMealsResult = MutableSharedFlow<Resource<Meal>>()
    val getMealsResult = _getMealsResult.asSharedFlow()

    private var job: Job?  = null

    fun getMeals(query: String) {
        job?.cancel()
        job = viewModelScope.launch {
            getMealsUseCase(query).collect {
                _getMealsResult.emit(it)
            }
        }
    }
}
