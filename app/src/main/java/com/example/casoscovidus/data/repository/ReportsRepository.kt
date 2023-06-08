package com.example.casoscovidus.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.casoscovidus.MyApp
import com.example.casoscovidus.data.local.database.AppDatabase
import com.example.casoscovidus.data.models.Report
import com.example.casoscovidus.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReportsRepository {
    private val database = AppDatabase.getDatabase(MyApp.getContext())
    val reports = MutableLiveData<List<Report>?>()
    val favorites = MutableLiveData<List<Report>?>()

    // Fetches data from services and stores response in local database if successful
    suspend fun fetchReports() {
        return withContext(Dispatchers.IO) {
            val reportsResponse = RetrofitClient.reportsService.getReports()
            if (reportsResponse.isSuccessful) {
                val reportList = reportsResponse.body()
                if (reportsResponse.code() == 200 && reportList != null) {
                    database.reportDao().insertReports(reportList)
                }
            }
        }
    }

    // Retrieves all reports from local database if there are no reports try fetching new reports
    suspend fun getReports() {
        return withContext(Dispatchers.IO) {
            var reportList = database.reportDao().getReports()
            if (reportList.isEmpty()) {
                fetchReports()
                reportList = database.reportDao().getReports()
            }
            withContext(Dispatchers.Main) {
                reports.value = reportList
            }
        }
    }

    // Retrieves favorite reports from local database
    suspend fun getFavorites() {
        return withContext(Dispatchers.IO) {
            val favoriteList = database.reportDao().getFavorites()
            withContext(Dispatchers.Main) {
                favorites.value = favoriteList
            }
        }
    }

    // Updates the value of isFavorite of a report in local database
    suspend fun updateFavoriteFieldOfReport(reportId: String, isFavorite: Boolean) {
        return withContext(Dispatchers.IO) {
            val report = database.reportDao().getReport(reportId).copy(isFavorite = isFavorite)
            database.reportDao().updateReport(report)
        }
    }
}