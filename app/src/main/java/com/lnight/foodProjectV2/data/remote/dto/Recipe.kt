package com.lnight.foodProjectV2.data.remote.dto

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Recipe(
    val image_url: String,
    val publisher: String,
    val publisher_url: String,
    @PrimaryKey(autoGenerate = false)
    val recipe_id: String,
    val social_rank: Double,
    val source_url: String,
    val title: String
) : Parcelable