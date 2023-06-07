package com.example.casoscovidus.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "reports")
data class Report(
    @PrimaryKey
    @SerializedName("hash")
    val id: String,
    val date: Long,
    val positive: Long,
    @ColumnInfo(name = "positive_increase")
    val positiveIncrease: Long,
    val death: Long,
    @ColumnInfo(name = "death_increase")
    val deathIncrease: Long,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean,
)
