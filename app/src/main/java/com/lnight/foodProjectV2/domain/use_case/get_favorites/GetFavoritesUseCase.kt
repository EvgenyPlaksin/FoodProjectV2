package com.lnight.foodProjectV2.domain.use_case.get_favorites

import android.util.Log
import com.lnight.foodProjectV2.common.Resource
import com.lnight.foodProjectV2.data.remote.dto.Recipe
import com.lnight.foodProjectV2.domain.repository.local.DbMealsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.CancellationException

class GetFavoritesUseCase(
    private val repository: DbMealsRepository
) {
     operator fun invoke(): Flow<Resource<List<Recipe>>> = flow {
         emit(Resource.Loading())
         try {
                 val data = repository.getFavourites()
                 Log.e("TAG", "usecase -> ${data}")
                 emit(Resource.Success(data))

         } catch (e: Exception) {
             Log.e("TAG", "usecase error -> ${e.localizedMessage}")
             if(e is CancellationException) throw e
             emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
         }
        }
}