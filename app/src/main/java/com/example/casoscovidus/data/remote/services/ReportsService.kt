package com.example.casoscovidus.data.remote.services

import com.example.casoscovidus.data.models.Report
import retrofit2.http.GET

interface ReportsService {
    @GET ("v1/us/daily.json")
    suspend fun getReports(): List<Report>
}