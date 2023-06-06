package com.example.casoscovidus.data.models

data class Report(
    val date: Long,
    val positive: Long,
    val positiveIncrease: Long,
    val death: Long,
    val deathIncrease: Long,
    val isFavorite: Boolean,
)
