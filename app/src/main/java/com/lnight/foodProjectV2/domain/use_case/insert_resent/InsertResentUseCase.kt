package com.lnight.foodProjectV2.domain.use_case.insert_resent

import com.lnight.foodProjectV2.domain.model.Resent
import com.lnight.foodProjectV2.domain.repository.local.DbMealsRepository

class InsertResentUseCase(
    private val repository: DbMealsRepository
) {
    suspend operator fun invoke(resent: Resent) {
        repository.insertResent(resent)
    }
}