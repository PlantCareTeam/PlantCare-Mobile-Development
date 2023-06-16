package com.example.plantcare.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.plantcare.ui.screen.camera.CameraOption
import com.ujizin.camposer.state.CaptureMode
import java.io.File


@Composable
fun ActionBox(
    modifier: Modifier = Modifier,
    cameraOption: CameraOption,
    onTakePicture: () -> Unit,
    lastPicture: File?,
) {
    Box(modifier = modifier){
        PictureAction(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(top = 24.dp, bottom = 32.dp),
            onTakePicture = onTakePicture,
            lastPicture
        )
    }

}