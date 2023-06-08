package com.example.casoscovidus.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.casoscovidus.data.models.Report
import com.example.casoscovidus.data.repository.ReportsRepository
import kotlinx.coroutines.launch
import java.util.Date

class ReportsViewModel : ViewModel() {
    private val repository = ReportsRepository()
    private val _lastChecked = MutableLiveData<Date>()
    val lastChecked: LiveData<Date> = _lastChecked
    val newReports: LiveData<List<Report>> = repository.newReports
    val reports: LiveData<List<Report>> = repository.reports
    val favorites: LiveData<List<Report>> = repository.favorites

    init {
        fetchReports()
    }

    fun fetchReports() {
        _lastChecked.value = Date()
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