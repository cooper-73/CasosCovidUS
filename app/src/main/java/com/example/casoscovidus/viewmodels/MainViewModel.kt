package com.example.casoscovidus.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.casoscovidus.utils.FragmentType

class MainViewModel : ViewModel() {
    private val _fragmentSelected = MutableLiveData(FragmentType.ALL)
    val fragmentSelected: LiveData<FragmentType> = _fragmentSelected

    // Sets which tab was selected to render
    fun setFragmentSelected(fragmentSelected: FragmentType) {
        if (_fragmentSelected.value != fragmentSelected) {
            _fragmentSelected.value = fragmentSelected
        }
    }
}