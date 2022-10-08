package com.lnight.foodProjectV2.domain.use_case.get_resents

import com.lnight.foodProjectV2.common.Resource
import com.lnight.foodProjectV2.domain.model.Resent
import com.lnight.foodProjectV2.domain.repository.local.DbMealsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.CancellationException

class GetResentsUseCase(
    private val repository: DbMealsRepository
) {
    operator fun invoke(): Flow<Resource<List<Resent>>> = flow {
        emit(Resource.Loading())
        try {
            val data = repository.getResents()
            emit(Resource.Success(data))
        } catch (e: Exception) {
            if(e is CancellationException) throw e
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }
}