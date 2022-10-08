package com.lnight.foodProjectV2.domain.use_case.get_meal

import com.lnight.foodProjectV2.common.Resource
import com.lnight.foodProjectV2.data.remote.dto.DetailMeal
import com.lnight.foodProjectV2.domain.repository.remote.MealsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetRecipeUseCase (
    private val repository : MealsRepository
) {
    operator fun invoke(rId : Int) : Flow<Resource<DetailMeal>> = flow {
        try{
            emit(Resource.Loading())
            val meal = repository.getRecipe(rId)
            emit(Resource.Success(meal))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?:"An unexpected error occured"))
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }


    }
}