package com.lnight.foodProjectV2.presentation.search_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lnight.foodProjectV2.common.Resource
import com.lnight.foodProjectV2.domain.model.Resent
import com.lnight.foodProjectV2.domain.use_case.MealsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val mealsUseCases: MealsUseCases
): ViewModel() {
    
    private val _getResentsResult = MutableSharedFlow<Resource<List<Resent>>>()
    val getResentsResult = _getResentsResult.asSharedFlow()
    
    fun getResents() {
        viewModelScope.launch { 
            mealsUseCases.getResentsUseCase().collect {
                _getResentsResult.emit(it)
            }
        }
    }

    fun removeResent(resent: Resent) {
        viewModelScope.launch {
            mealsUseCases.removeResentsUseCase(resent)
        }
    }
}