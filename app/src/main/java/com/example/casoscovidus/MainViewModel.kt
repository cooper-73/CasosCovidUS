package com.example.casoscovidus

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _isAllListSelected = MutableLiveData(true)
    val isAllListSelected = _isAllListSelected

    fun markAllListSelected(isSelected: Boolean) {
        _isAllListSelected.value = isSelected
    }
}