package com.example.plantcare.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcare.data.CategoryRepository
import com.example.plantcare.model.categories.Category
import com.example.plantcare.model.histories.History
import com.example.plantcare.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel (private val rep : CategoryRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Category>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Category>>>
        get() = _uiState

    private val _hState: MutableStateFlow<UiState<List<History>>> = MutableStateFlow(UiState.Loading)
    val hState: StateFlow<UiState<List<History>>>
        get() = _hState

    fun getAllCategory(){
        viewModelScope.launch {
            rep.getAllCategories()
                .catch {
                    _uiState.value =UiState.Error(it.message.toString())
                }
                .collect{categories ->
                    _uiState.value = UiState.Success(categories)
                }
        }
    }

    fun getAllHistory(){
        viewModelScope.launch {
            rep.getAllHistories()
                .catch {
                    _hState.value =UiState.Error(it.message.toString())
                }
                .collect{histories ->
                    _hState.value = UiState.Success(histories)
                }
        }
    }
}