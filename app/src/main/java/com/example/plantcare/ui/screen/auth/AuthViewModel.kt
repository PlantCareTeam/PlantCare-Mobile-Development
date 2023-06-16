package com.example.plantcare.ui.screen.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcare.data.MainRepository
import com.example.plantcare.model.user.LoginRequest
import com.example.plantcare.model.user.RegisterRequest
import com.example.plantcare.ui.common.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel (
    private val rep: MainRepository
) : ViewModel() {

    private val _uiStateRegis: MutableStateFlow<UiState<RegisterRequest>> = MutableStateFlow(UiState.Loading)
    val uiStateRegis: StateFlow<UiState<RegisterRequest>>
        get() = _uiStateRegis

    private val _uiStateLogin: MutableStateFlow<UiState<LoginRequest>> = MutableStateFlow(UiState.Loading)
    val uiStateLogin: StateFlow<UiState<LoginRequest>>
        get() = _uiStateLogin

    private val _loading = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val loading: MutableStateFlow<UiState<Boolean>> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: MutableStateFlow<String?> = _error


    fun signUp(registerRequest: RegisterRequest, onSucces: () -> Unit) = viewModelScope.launch {
        _uiStateRegis.value = UiState.Loading
        viewModelScope.launch { Dispatchers.IO }
        try{
            rep.registerUser(registerRequest).catch {
                    error ->
                _uiStateRegis.value = UiState.Error("error: ${error.message}")
            }.collect{
                _uiStateRegis.value = UiState.Success(registerRequest)
                Log.d("LoginPage", "signIn: +${_uiStateRegis.value}")
                withContext(Dispatchers.Main){
                    onSucces()
                }
            }
        } catch (e : Exception) {
            _error.value
            Log.d("TAG", "signUp: ${e.message}")
        }
    }

    fun loginUser(loginRequest: LoginRequest, onSucces: () -> Unit) = viewModelScope.launch {
            _uiStateLogin.value = UiState.Loading
            viewModelScope.launch { Dispatchers.IO }
            try{
                rep.loginUser(loginRequest).catch {
                        error ->
                    _uiStateLogin.value = UiState.Error("error: ${error.message}")
                }.collect{
                    _uiStateLogin.value = UiState.Success(loginRequest)
                    Log.d("LoginPage", "signIn: +${_uiStateLogin.value}")
                    withContext(Dispatchers.Main){
                        onSucces()
                    }
                }
            } catch (e : Exception) {
                _error.value
                Log.d("TAG", "loginUser: ${e.message}")
            }
    }


    fun saveUser(user: String) {
        viewModelScope.launch {
            rep.saveUserData(user)
        }
    }

    fun logout() {
        viewModelScope.launch {
            rep.logout()
        }
    }
}