package com.example.casoscovidus.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.casoscovidus.data.models.Report
import com.example.casoscovidus.data.repository.ReportsRepository
import com.example.casoscovidus.utils.FetchingStatus
import com.example.casoscovidus.utils.LoadingStatus
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import java.util.Date

class ReportsViewModel : ViewModel() {
    private val repository = ReportsRepository()

    private val _lastChecked = MutableLiveData<Date>()
    val lastChecked: LiveData<Date> = _lastChecked
    private val _loadingStatus = MutableLiveData<LoadingStatus>()
    val loadingStatus: LiveData<LoadingStatus> = _loadingStatus
    private val _fetchingStatus = MutableLiveData<FetchingStatus>()
    val fetchingStatus: LiveData<FetchingStatus> = _fetchingStatus

    val reports: LiveData<List<Report>> = repository.reports
    val favorites: LiveData<List<Report>> = repository.favorites

    init {
        fetchReports()
    }

    fun fetchReports() {
        _lastChecked.value = Date()
        viewModelScope.launch {
            try {
                _fetchingStatus.value = FetchingStatus.LOADING
                repository.fetchReports()
                _fetchingStatus.value = FetchingStatus.DONE
            } catch (e: UnknownHostException) {
                _fetchingStatus.value = FetchingStatus.NO_INTERNET_CONNECTION
            } catch (e: Exception) {
                _fetchingStatus.value = FetchingStatus.ERROR
            }
        }
    }

    fun loadReports() {
        viewModelScope.launch {
            try {
                _loadingStatus.value = LoadingStatus.LOADING
                repository.getReports()
                _loadingStatus.value = LoadingStatus.DONE
            } catch (e: Exception) {
                _loadingStatus.value = LoadingStatus.ERROR
            }
        }
    }

    fun loadFavorites() {
        viewModelScope.launch {
            try {
                _loadingStatus.value = LoadingStatus.LOADING
                repository.getFavorites()
                _loadingStatus.value = LoadingStatus.DONE
            } catch (e: Exception) {
                _loadingStatus.value = LoadingStatus.ERROR
            }
        }
    }

    fun setFavoriteFieldOfReport(reportId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.updateFavoriteFieldOfReport(reportId, isFavorite)
        }
    }
}