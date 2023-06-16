package com.example.plantcare.di

import android.content.Context
import com.example.plantcare.api.ApiConfig
import com.example.plantcare.data.CategoryRepository
import com.example.plantcare.data.MainRepository
import com.example.plantcare.data.UserPreferences
import com.example.plantcare.data.dataStore

object Injection {
    fun provideRepository(context: Context): MainRepository {
        val preferences = UserPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return MainRepository.getInstance(preferences, apiService, context)
    }
    fun provideRepository(): CategoryRepository {
        return CategoryRepository.getInstance()
    }
}