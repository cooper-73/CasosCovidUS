package com.example.casoscovidus.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.casoscovidus.data.models.Report
import com.example.casoscovidus.data.repository.ReportsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReportsViewModel : ViewModel() {
    private val repository = ReportsRepository()
    
    val reports: LiveData<List<Report>> = repository.reports

    private val _favorites = MutableLiveData<List<Report>>()
    val favorites: LiveData<List<Report>> = _favorites

    fun loadReports() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.fetchReportsList()
            }
        }
    }

    fun loadFavorites() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.fetchReportsList()
            }
            _favorites.value = reports.value?.filter { report -> report.isFavorite } ?: listOf()
        }
    }
}