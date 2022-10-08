package com.lnight.foodProjectV2.domain.use_case.get_meals

import android.util.Log
import com.lnight.foodProjectV2.common.Resource
import com.lnight.foodProjectV2.data.remote.dto.toMeal
import com.lnight.foodProjectV2.domain.model.Meal
import com.lnight.foodProjectV2.domain.repository.remote.MealsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetMealsUseCase (
    private val repository : MealsRepository
) {
    operator fun invoke(query: String) : Flow<Resource<Meal>> = flow{
        try{
            emit(Resource.Loading())
            val meals = repository.getMeals((query))
            emit(Resource.Success(meals.toMeal()))
        }catch (e: HttpException){
            if(e.localizedMessage == "HTTP 400 Bad Request") {
                emit(Resource.Error("Nothing found"))
            } else {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
            }
            Log.e("TAG", "e -> ${e.localizedMessage}")
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }

    }
}