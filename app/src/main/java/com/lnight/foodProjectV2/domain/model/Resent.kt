package com.lnight.foodProjectV2.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Resent(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val timeStamp: Long,
    val title: String
)
