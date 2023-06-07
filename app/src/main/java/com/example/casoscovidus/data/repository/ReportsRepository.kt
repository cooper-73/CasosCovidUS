package com.example.casoscovidus.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.casoscovidus.data.models.Report
import com.example.casoscovidus.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReportsRepository {
    var reports = MutableLiveData<List<Report>>()

    suspend fun fetchReportsList() {
        return withContext(Dispatchers.IO) {
            val reportsResponse = RetrofitClient.reportsService.getReports()
            withContext(Dispatchers.Main) {
                reports.value = reportsResponse
            }
        }
    }
}