package com.example.plantcare.ui.screen.camera

import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AppPermission(
    navController: () -> Unit,
) {
    val permissionsState = rememberMultiplePermissionsState(
        mutableListOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.RECORD_AUDIO,
        ).apply {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    )

    if (permissionsState.allPermissionsGranted) {
        navController
    } else {
        LaunchedEffect(Unit) {
            permissionsState.launchMultiplePermissionRequest()
        }
    }

    LaunchedEffect(Unit) {
        permissionsState.launchMultiplePermissionRequest()
    }
}
