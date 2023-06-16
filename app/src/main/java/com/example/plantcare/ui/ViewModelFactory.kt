package com.example.plantcare.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.plantcare.data.CategoryRepository
import com.example.plantcare.ui.screen.detail.DetailViewModel
import com.example.plantcare.ui.screen.home.HomeViewModel
import com.example.plantcare.ui.screen.scanTomato.ScanViewModel

class ViewModelFactory (private val repository: CategoryRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}