package com.example.plantcare.ui.screen.scanTomato

import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcare.data.MainRepository
import com.example.plantcare.helper.FileDataSource
import com.example.plantcare.model.predict.Deskripsi
import com.example.plantcare.ui.common.UiState
import com.ujizin.camposer.state.CameraState
import com.ujizin.camposer.state.ImageCaptureResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class ScanViewModel (private val rep : MainRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Deskripsi>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Deskripsi>>
        get() = _uiState

    private val _uiStateImage: MutableStateFlow<UiState<MultipartBody.Part>> = MutableStateFlow(UiState.Loading)
    val uiStateImage: StateFlow<UiState<MultipartBody.Part>>
        get() = _uiStateImage

    private val _loading = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val loading: MutableStateFlow<UiState<Boolean>> = _loading

    fun getImage(){
        viewModelScope.launch {
            try {
                rep.getImage()
                    .catch { error ->
                        Log.d("Scan", "getImage: ${error.message}")
                        _uiStateImage.value = UiState.Error("error: ${error.message}")
                    }.collect{
                        _uiStateImage.value = UiState.Success(it)
                        Log.d("Image", "getImageCaptured success: ${it}")
                    }
            } catch (e : Exception){

            }
        }
    }

    fun setImage(image : File?, onSuccess: () -> Unit){
        viewModelScope.launch {
            try {
                val imageScan = image?.asRequestBody("image/jpg".toMediaTypeOrNull())
                val imageName = "image"
                val imagePart = MultipartBody.Part.createFormData(
                    imageName, image!!.name, imageScan!!
                )
                val image = imagePart
                rep.setImage(image)
                _uiStateImage.value = UiState.Loading
                withContext(Dispatchers.Main){
                    onSuccess()
                }
            }catch (e:Exception){
                Log.d("TAG", "setImage: ${e.message}")
            }
        }
    }

    fun predictTomato(image: MultipartBody.Part, onSuccess: () -> Unit)
    = viewModelScope.launch{
        _uiState.value = UiState.Loading
        viewModelScope.launch {Dispatchers.IO}
        try {
            rep.predictTomato(image).catch {
                error ->
                _uiState.value = UiState.Error("error: ${error.message}")
            }.collect{deskripsi ->
                _uiState.value = UiState.Success(deskripsi)
                Log.d("LoginPage", "signIn: +${_uiState.value}")
                withContext(Dispatchers.Main){
                    onSuccess()
                }
            }
        }catch (e : Exception) {
            _uiState.value = UiState.Error("error: ${e.message}")
            Log.d("TAG", "predictTomato: ${e.message}")
        }
    }

}
