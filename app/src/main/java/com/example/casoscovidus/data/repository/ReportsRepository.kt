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
    val newReports = MutableLiveData<List<Report>>()
    val reports = MutableLiveData<List<Report>>()
    val favorites = MutableLiveData<List<Report>>()

    suspend fun fetchReports() {
        return withContext(Dispatchers.IO) {
            val reportsResponse = RetrofitClient.reportsService.getReports()
            database.reportDao().insertReports(reportsResponse)
            withContext(Dispatchers.Main) {
                newReports.value = reportsResponse
            }
        }
    }

    suspend fun getReports() {
        return withContext(Dispatchers.IO) {
            val reportList = database.reportDao().getReports()
            withContext(Dispatchers.Main) {
                reports.value = reportList
            }
        }
    }

    suspend fun getFavorites() {
        return withContext(Dispatchers.IO) {
            val favoriteList = database.reportDao().getFavorites()
            withContext(Dispatchers.Main) {
                favorites.value = favoriteList
            }
        }
    }

    suspend fun updateFavoriteFieldOfReport(reportId: String, isFavorite: Boolean) {
        return withContext(Dispatchers.IO) {
            val report = database.reportDao().getReport(reportId).copy(isFavorite = isFavorite)
            database.reportDao().updateReport(report)
        }
    }
}