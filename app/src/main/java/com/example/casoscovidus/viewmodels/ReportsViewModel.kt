package com.example.casoscovidus.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.casoscovidus.data.models.Report

class ReportsViewModel : ViewModel() {
    private val _reports = MutableLiveData<List<Report>>()
    val reports: LiveData<List<Report>> = _reports

    private val _favorites = MutableLiveData<List<Report>>()
    val favorites: LiveData<List<Report>> = _favorites

    fun loadReports() {
        _reports.value = listOf(
            Report(1L, 2L, 3L, 4L, 5L, true),
            Report(2L, 3L, 4L, 5L, 6L, false),
            Report(3L, 2L, 3L, 4L, 5L, true),
            Report(4L, 3L, 4L, 5L, 6L, false),
            Report(5L, 2L, 3L, 4L, 5L, true),
            Report(6L, 3L, 4L, 5L, 6L, false),
            Report(7L, 2L, 3L, 4L, 5L, true),
            Report(8L, 3L, 4L, 5L, 6L, false),
            Report(9L, 2L, 3L, 4L, 5L, true),
        )
    }

    fun loadFavorites() {
        loadReports()
        _favorites.value = _reports.value?.filter { report -> report.isFavorite } ?: listOf()
    }
}