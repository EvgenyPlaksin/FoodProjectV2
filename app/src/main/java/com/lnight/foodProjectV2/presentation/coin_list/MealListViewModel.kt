package com.lnight.foodProjectV2.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lnight.foodProjectV2.common.Resource
import com.lnight.foodProjectV2.domain.model.Meal
import com.lnight.foodProjectV2.domain.model.Resent
import com.lnight.foodProjectV2.domain.use_case.MealsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealListViewModel @Inject constructor(
private val mealsUseCases: MealsUseCases
): ViewModel() {

    private val _getMealsResult = MutableSharedFlow<Resource<Meal>>()
    val getMealsResult = _getMealsResult.asSharedFlow()

    private var job: Job?  = null

    private val _getResentsResult = MutableSharedFlow<Resource<List<Resent>>>()
    val getResentsResult = _getResentsResult.asSharedFlow()

    fun getResents() {
        viewModelScope.launch {
            mealsUseCases.getResentsUseCase().collect {
                _getResentsResult.emit(it)
            }
        }
    }

    fun getMeals(query: String) {
        job?.cancel()
        job = viewModelScope.launch {
            mealsUseCases.getMealsUseCase(query).collect {
                _getMealsResult.emit(it)
            }
        }
    }

    fun addResent(resent: Resent) {
        viewModelScope.launch {
            mealsUseCases.insertResentUseCase(resent)
            mealsUseCases.removeResentsUseCase()
        }
    }

}
