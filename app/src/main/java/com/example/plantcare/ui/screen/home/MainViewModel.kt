package com.example.plantcare.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcare.data.CategoryRepository
import com.example.plantcare.data.MainRepository
import com.example.plantcare.model.user.DataItem
import com.example.plantcare.model.user.UserModel
import com.example.plantcare.model.user.UserResponse
import com.example.plantcare.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel (private val rep : MainRepository) : ViewModel() {
    private val _uiState : MutableStateFlow<UiState<DataItem>> = MutableStateFlow(UiState.Loading)
    val uiState : StateFlow<UiState<DataItem>>
        get() = _uiState
    var isLogin: MutableStateFlow<String> = MutableStateFlow("")

    fun saveUser(user: String) {
        viewModelScope.launch {
            rep.saveUserData(user)
        }
    }

    fun getUser(){
        viewModelScope.launch {
            rep.getUserData()
        }
    }

    fun checkToken(){
        viewModelScope.launch {
            isLogin.value = rep.getToken()
        }
    }

    fun logout() {
        viewModelScope.launch {
            rep.logout()
        }
    }


}