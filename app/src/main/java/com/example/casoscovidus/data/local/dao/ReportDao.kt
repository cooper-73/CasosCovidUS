package com.example.casoscovidus.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.casoscovidus.data.models.Report

@Dao
interface ReportDao {
    @Query("SELECT * FROM reports")
    suspend fun getReports(): List<Report>

    @Query("SELECT * FROM reports WHERE is_favorite = 1")
    suspend fun getFavorites(): List<Report>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertReports(reports: List<Report>)

    @Update
    suspend fun updateReport(report: Report)
}