package com.example.plantcare.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.plantcare.data.MainRepository
import com.example.plantcare.ui.screen.auth.AuthViewModel
import com.example.plantcare.ui.screen.home.MainViewModel
import com.example.plantcare.ui.screen.scanTomato.ScanViewModel

class MainViewModelFactory (private val repository: MainRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(ScanViewModel::class.java)) {
            return ScanViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}