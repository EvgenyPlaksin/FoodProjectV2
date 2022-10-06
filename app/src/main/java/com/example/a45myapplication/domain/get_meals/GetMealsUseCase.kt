package com.example.a45myapplication.domain.get_meals

import android.util.Log
import com.example.a45myapplication.common.Resource
import com.example.a45myapplication.data.remote.dto.toMeal
import com.example.a45myapplication.domain.model.Meal
import com.example.a45myapplication.domain.repository.MealsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMealsUseCase @Inject constructor(
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