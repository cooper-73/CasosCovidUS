package com.example.casoscovidus.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.casoscovidus.data.models.Report
import com.example.casoscovidus.data.repository.ReportsRepository
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import java.util.Date

class ReportsViewModel : ViewModel() {
    private val repository = ReportsRepository()
    private val _lastChecked = MutableLiveData<Date>()
    val lastChecked: LiveData<Date> = _lastChecked
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    val isRefreshing = MutableLiveData<Boolean>()
    val reports: LiveData<List<Report>> = repository.reports
    val favorites: LiveData<List<Report>> = repository.favorites

    init {
        fetchReports()
    }

    fun fetchReports() {
        _lastChecked.value = Date()
        viewModelScope.launch {
            try {
                isRefreshing.value = true
                repository.fetchReports()
                isRefreshing.value = false
            } catch (e: UnknownHostException) {
                isRefreshing.value = false
            }
        }
    }

    fun loadReports() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getReports()
            _isLoading.value = false
        }
    }

    fun loadFavorites() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getFavorites()
            _isLoading.value = false
        }
    }

    fun setFavoriteFieldOfReport(reportId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.updateFavoriteFieldOfReport(reportId, isFavorite)
        }
    }
}