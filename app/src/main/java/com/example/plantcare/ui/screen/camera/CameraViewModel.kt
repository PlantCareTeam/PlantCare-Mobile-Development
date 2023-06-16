package com.example.plantcare.ui.screen.camera

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcare.data.MainRepository
import com.example.plantcare.helper.FileDataSource
import com.ujizin.camposer.state.CameraState
import com.ujizin.camposer.state.ImageCaptureResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

abstract class CameraViewModel (
    private val pref : MainRepository
    ): ViewModel() {


    abstract val fileDataSource: FileDataSource

    private val _uiState: MutableStateFlow<CameraUiState> = MutableStateFlow(CameraUiState.Initial)
    val uiState: StateFlow<CameraUiState> get() = _uiState

    private lateinit var user: Flow<String>

    private fun initCamera() {
        viewModelScope.launch {
            pref.getUserData()
                .onStart {
                    CameraUiState.Initial }
                .collect { user ->
                    _uiState.value = CameraUiState.Ready(user, fileDataSource.lastPicture).apply {
                        this@CameraViewModel.user = user
                    }
                }
        }
    }

    fun takePicture(cameraState: CameraState) = with(cameraState) {
        viewModelScope.launch {
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> takePicture(
                    fileDataSource.imageContentValues,
                    onResult = ::onImageResult
                )

                else -> takePicture(
                    fileDataSource.getFile("jpg"),
                    ::onImageResult
                )
            }
        }
    }

    private fun onImageResult(imageResult: ImageCaptureResult) {
        when (imageResult) {
            is ImageCaptureResult.Error -> onError(imageResult.throwable)
            is ImageCaptureResult.Success -> captureSuccess()
        }
    }

    private fun captureSuccess() {
        viewModelScope.launch {
            _uiState.update {
                CameraUiState.Ready(user = user, lastPicture = fileDataSource.lastPicture)
            }
        }
    }

    private fun onError(throwable: Throwable?) {
        _uiState.update { CameraUiState.Ready(user, fileDataSource.lastPicture, throwable) }
    }

    sealed interface CameraUiState {
        object Initial : CameraUiState
        data class Ready(
            val user: Flow<String>,
            val lastPicture: File?,
            val throwable: Throwable? = null,
            val qrCodeText: String? = null,
        ) : CameraUiState

    }
}