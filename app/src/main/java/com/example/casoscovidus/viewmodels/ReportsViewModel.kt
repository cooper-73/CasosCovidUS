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

    // Handles date of last data retrieval attempt
    private val _lastChecked = MutableLiveData<Date>()
    val lastChecked: LiveData<Date> = _lastChecked

    // Handles the state of loading process
    private val _loadingStatus = MutableLiveData<LoadingStatus>()
    val loadingStatus: LiveData<LoadingStatus> = _loadingStatus

    // Handles the state of fetching process
    private val _fetchingStatus = MutableLiveData<FetchingStatus>()
    val fetchingStatus: LiveData<FetchingStatus> = _fetchingStatus

    val reports: LiveData<List<Report>> = repository.reports
    val favorites: LiveData<List<Report>> = repository.favorites
    val isDataEmpty: LiveData<Boolean> = repository.isDataEmpty

    // Fetches and retrieves all reports
    fun fetchAngGetReports() {
        viewModelScope.launch {
            _lastChecked.value = Date()
            try {
                _fetchingStatus.value = FetchingStatus.LOADING
                repository.fetchReports()
                _fetchingStatus.value = FetchingStatus.DONE
                loadReports()
            } catch (e: UnknownHostException) {
                _fetchingStatus.value = FetchingStatus.NO_INTERNET_CONNECTION
            } catch (e: Exception) {
                _fetchingStatus.value = FetchingStatus.ERROR
            } finally {
                repository.setDataEmpty(checkIfDataIsEmpty())
            }
        }
    }

    // Retrieves all reports
    fun loadReports() {
        viewModelScope.launch {
            _lastChecked.value = Date()
            try {
                _loadingStatus.value = LoadingStatus.LOADING
                repository.getReports()
                _loadingStatus.value = LoadingStatus.DONE
            } catch (e: UnknownHostException) {
                _loadingStatus.value = LoadingStatus.NO_INTERNET_CONNECTION
            } catch (e: Exception) {
                _loadingStatus.value = LoadingStatus.ERROR
            } finally {
                repository.setDataEmpty(checkIfDataIsEmpty())
            }
        }
    }

    // Retrieves favorite reports
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

    // Updates a report's isFavorite value
    fun setFavoriteFieldOfReport(reportId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.updateFavoriteFieldOfReport(reportId, isFavorite)
        }
    }

    // Checks if reports is null or empty
    private fun checkIfDataIsEmpty() = reports.value?.isEmpty() ?: true

    // Updates status of data length
    fun setDataEmptyStatus(isEmpty: Boolean) {
        repository.setDataEmpty(isEmpty)
    }
}