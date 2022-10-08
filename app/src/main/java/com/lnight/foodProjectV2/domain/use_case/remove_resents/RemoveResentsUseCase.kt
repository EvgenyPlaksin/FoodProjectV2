package com.lnight.foodProjectV2.domain.use_case.remove_resents

import com.lnight.foodProjectV2.domain.model.Resent
import com.lnight.foodProjectV2.domain.repository.local.DbMealsRepository

class RemoveResentsUseCase(
    private val repository: DbMealsRepository
) {
    suspend operator fun invoke(resent: Resent? = null) {
        if(resent == null) {
            repository.removeOldResents()
        } else {
            repository.removeResent(resent)
        }
    }
}