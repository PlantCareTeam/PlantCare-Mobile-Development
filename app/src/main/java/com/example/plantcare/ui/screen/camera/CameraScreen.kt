package com.example.plantcare.ui.screen.camera

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import com.example.plantcare.R
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toFile
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.plantcare.di.Injection
import com.example.plantcare.ui.ViewModelFactory
import com.example.plantcare.ui.common.UiState
import com.example.plantcare.ui.components.ActionBox
import com.example.plantcare.ui.navigation.Screen
import com.example.plantcare.ui.screen.MainViewModelFactory
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.ujizin.camposer.CameraPreview
import com.ujizin.camposer.state.CamSelector
import com.ujizin.camposer.state.rememberCamSelector
import com.ujizin.camposer.state.rememberCameraState
import com.example.plantcare.ui.screen.camera.CameraViewModel.CameraUiState
import com.example.plantcare.ui.screen.scanTomato.ScanViewModel
import com.ujizin.camposer.extensions.takePicture
import com.ujizin.camposer.state.CameraState
import com.ujizin.camposer.state.CaptureMode
import com.ujizin.camposer.state.ImageCaptureResult
import com.ujizin.camposer.state.rememberImageAnalyzer
import com.ujizin.camposer.state.rememberTorch
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.io.File


@Composable
fun CameraScreen(
    viewModel: ScanViewModel = viewModel(
        factory = MainViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
    onTakePicture: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val result: CameraUiState
    val cameraState = rememberCameraState()
    var camSelector by rememberCamSelector(CamSelector.Back)
    var cameraOption by rememberSaveable { mutableStateOf(CameraOption.Photo) }
    var lastPicture: File? = null
    var uri: Uri? = null
    CameraSection(
        cameraState = cameraState,
        lastPicture = lastPicture,
        onTakePicture = {
            try {

                coroutineScope.launch {
                    cameraState.takePicture(File.createTempFile("test", "test")) { result: ImageCaptureResult ->
                        when(result){
                            is ImageCaptureResult.Success -> {
                                val imageUri = result.savedUri
                                viewModel.setImage(imageUri?.toFile()){
                                    onTakePicture()
                                }
                            }
                            is ImageCaptureResult.Error -> Toast.makeText(context, "error: ${result.throwable}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d("camera", "CameraScreen: $e")
            }
        },
    )
}


@Composable
fun CameraSection(
    cameraState: CameraState,
    lastPicture: File?,
    onTakePicture: () -> Unit,
) {
    var camSelector by rememberCamSelector(CamSelector.Back)
    var cameraOption by rememberSaveable { mutableStateOf(CameraOption.Photo) }
    CameraPreview(
        cameraState = cameraState,
        camSelector = camSelector,
        captureMode = cameraOption.toCaptureMode(),
    ) {
        CameraInnerContent(
            Modifier.fillMaxSize(),
            cameraOption = cameraOption,
            lastPicture = lastPicture,
            onTakePicture = onTakePicture,
        )

    }
}

@Composable
fun CameraInnerContent(
    modifier: Modifier = Modifier,
    cameraOption: CameraOption,
    lastPicture: File?,
    onTakePicture: () -> Unit,
) {
    Box(
        modifier = modifier,
    ) {
        ActionBox(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp, top = 16.dp),
            lastPicture = lastPicture,
            cameraOption = cameraOption,
            onTakePicture = onTakePicture,
        )
    }
}


enum class CameraOption(@StringRes val titleRes: Int) {
    Photo(R.string.photo);

    fun toCaptureMode(): CaptureMode = when (this) {
        Photo -> CaptureMode.Image
    }
}
