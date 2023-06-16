package com.example.plantcare.ui.screen.scanTomato


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.commandiron.compose_loading.CubeGrid
import com.example.plantcare.R
import com.example.plantcare.di.Injection
import com.example.plantcare.ui.ViewModelFactory
import com.example.plantcare.ui.common.UiState
import com.example.plantcare.ui.navigation.Screen
import com.example.plantcare.ui.screen.MainViewModelFactory
import com.example.plantcare.ui.theme.PlantCareTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.io.File


@Composable
fun ScanAppleScreen(
    viewModel: ScanViewModel = viewModel(
        factory = MainViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
    navigateBack: () -> Unit,
    navController: NavController,
    modifier : Modifier = Modifier
){
    var getFile : File? by rememberSaveable { mutableStateOf(null) }
    var loadingScan by rememberSaveable { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = modifier
            .fillMaxSize()
    ){
        Column(modifier = modifier) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .background(color = Color.White)
            ) {
                IconButton(
                    onClick = navigateBack,
                    modifier = modifier
                        .padding(start = 8.dp, top = 16.dp)
                        .clip(CircleShape)
                        .size(40.dp)
                        .testTag("back_button")
                        .background(Color.White)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back",
                    )
                }
                Text(
                    text = "Deteksi Penyakit",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(end = 32.dp)
                        .align(CenterVertically)
                        .weight(1f)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .padding(horizontal = 12.dp)
                    .padding(top = 20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .offset(y = (-100).dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = CenterHorizontally
                ) {
                    viewModel.uiStateImage.collectAsState(initial = UiState.Loading)
                        .value.let { uiState ->
                            when (uiState) {
                                is UiState.Loading -> {}
                                is UiState.Success -> {
                                    coroutineScope.launch {
                                        viewModel.getImage()
                                        viewModel.predictTomato(uiState.data) {
                                            navController.navigate(Screen.Diagnoses.route)
                                        }
                                    }
                                }
                                is UiState.Error -> {}
                            }
                        }

                    if (loadingScan) CubeGrid()
                    else AsyncImage(
                        model = getFile ?: "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        placeholder = painterResource(id = R.drawable.input_photo),
                        error = painterResource(id = R.drawable.input_photo),
                    )
                    if (!loadingScan) {
                        if (getFile == null) {
                            Text(
                                text = "Unggah Foto Daun Apel",
                                fontWeight = FontWeight.ExtraBold,
                                modifier = Modifier
                                    .align(CenterHorizontally)
                                    .padding(top = 8.dp, bottom = 8.dp)
                            )
                            Text(
                                text = stringResource(R.string.image_rules),
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(12.dp)
                            )
                            Spacer(modifier = Modifier.padding(top = 16.dp))
                            Row {
                                Button(
                                    onClick = {
                                        navController.navigate(Screen.Camera.route)
                                    },
                                    modifier = modifier
                                        .width(200.dp)
                                        .padding(horizontal = 20.dp)
                                ) {
                                    Text(text = "Camera")
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                Button(
                                    onClick = { /*TODO*/ },
                                    modifier = modifier
                                        .width(200.dp)
                                        .padding(horizontal = 20.dp)
                                ) {
                                    Text(text = "Gallery")
                                }
                            }
                            Spacer(modifier = Modifier.padding(top = 100.dp))
                            Button(
                                onClick = { navController.navigate(Screen.Diagnoses.route) },
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .align(CenterHorizontally)
                                    .padding(bottom = 60.dp)
                            ) {
                                Text(text = "Detect", modifier = Modifier)
                            }
                        }
                    }
                }


            }

        }

    }
}