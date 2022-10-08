package com.lnight.foodProjectV2.common

import com.lnight.foodProjectV2.data.remote.dto.Recipe
import com.lnight.foodProjectV2.domain.model.Resent

object Constants {
    const val  BASE_URL = "https://forkify-api.herokuapp.com/api/"
    var popBack = true
    var saveList = mutableListOf<Recipe>()
    var saveListResents = mutableListOf<Resent>()
}