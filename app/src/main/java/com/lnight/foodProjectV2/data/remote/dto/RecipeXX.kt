package com.lnight.foodProjectV2.data.remote.dto

data class RecipeXX(
    val image_url: String,
    val ingredients: List<String>,
    val publisher: String,
    val publisher_url: String,
    val recipe_id: String,
    val social_rank: Int,
    val source_url: String,
    val title: String
)