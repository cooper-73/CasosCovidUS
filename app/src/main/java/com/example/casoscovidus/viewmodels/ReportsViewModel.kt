package com.example.casoscovidus.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.casoscovidus.data.models.Report
import com.example.casoscovidus.data.repository.ReportsRepository
import kotlinx.coroutines.launch

class ReportsViewModel : ViewModel() {
    private val repository = ReportsRepository()

    val reports: LiveData<List<Report>> = repository.reports
    val favorites: LiveData<List<Report>> = repository.favorites

    init {
        fetchReports()
    }

    fun fetchReports() {
        viewModelScope.launch {
            repository.fetchReports()
        }
    }

    fun loadReports() {
        viewModelScope.launch {
            repository.getReports()
        }
    }

    fun loadFavorites() {
        viewModelScope.launch {
            repository.getFavorites()
        }
    }

    fun setFavoriteFieldOfReport(reportId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.updateFavoriteFieldOfReport(reportId, isFavorite)
        }
    }
}