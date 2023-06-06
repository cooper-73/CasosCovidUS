package com.example.casoscovidus.data.models

import com.google.gson.annotations.SerializedName

data class Report(
    @SerializedName("hash")
    val id: String,
    val date: Long,
    val positive: Long,
    val positiveIncrease: Long,
    val death: Long,
    val deathIncrease: Long,
    val isFavorite: Boolean,
)
