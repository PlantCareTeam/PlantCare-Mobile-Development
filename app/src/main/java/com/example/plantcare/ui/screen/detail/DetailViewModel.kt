package com.example.plantcare.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcare.data.CategoryRepository
import com.example.plantcare.model.categories.Category
import com.example.plantcare.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel (
    private val rep: CategoryRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Category>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Category>>
        get() = _uiState

    fun getCategoryById(id: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(rep.getCategoryById(id))
        }
    }

}