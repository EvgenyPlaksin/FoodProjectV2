package com.lnight.foodProjectV2.domain.model

import com.lnight.foodProjectV2.data.remote.dto.Recipe

data class Meal(
    val recipes: List<Recipe>
)

