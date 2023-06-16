package com.example.plantcare.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.plantcare.R
import com.example.plantcare.ui.navigation.Screen
import kotlinx.coroutines.delay
import java.io.File

@Composable
fun PictureAction(
    modifier: Modifier = Modifier,
    onTakePicture: () -> Unit,
    lastPicture: File?,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PictureButton(onClick = onTakePicture)
        GalleryButton(lastPicture = lastPicture) {
            
        }
    }
}

@Composable
fun GalleryButton(lastPicture : File?, onClick: () -> Unit) {
    var shouldAnimate by remember { mutableStateOf(false) }
    val animScale by animateFloatAsState(targetValue = if (shouldAnimate) 1.25F else 1F)
    AsyncImage(
        modifier = Modifier
            .scale(animScale)
            .size(48.dp)
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.5F), CircleShape)
            .clickable(onClick = onClick),
        contentScale = ContentScale.Crop,
        model = ImageRequest.Builder(LocalContext.current)
            .build(),
        contentDescription = stringResource(R.string.photo)
    )

    LaunchedEffect(lastPicture) {
        shouldAnimate = true
        delay(50)
        shouldAnimate = false
    }
}


@Composable
private fun PictureButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .size(80.dp)
            .border(BorderStroke(4.dp, Color.White), CircleShape)
            .padding(2.dp)
            .clip(CircleShape)
            .then(modifier),
        onClick = onClick
    ){

    }
}