package com.lnight.foodProjectV2.data.remote

import com.lnight.foodProjectV2.data.remote.dto.DetailMeal
import com.lnight.foodProjectV2.data.remote.dto.MealDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("/search?")
    suspend fun getMeals(@Query("q") q : String?) : MealDto
    @GET("/get?")
    suspend fun getRecipes(@Query("rId") rId : Int ) : DetailMeal
}