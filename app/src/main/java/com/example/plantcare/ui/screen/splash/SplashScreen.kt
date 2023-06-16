package com.example.plantcare.ui.screen.splash

import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.plantcare.R
import com.example.plantcare.di.Injection
import com.example.plantcare.ui.common.UiState
import com.example.plantcare.ui.navigation.Screen
import com.example.plantcare.ui.screen.MainViewModelFactory
import com.example.plantcare.ui.screen.home.MainViewModel
import com.example.plantcare.ui.theme.PlantCareTheme

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navController : NavController,
){
    val mainViewModel : MainViewModel = viewModel(
        factory = MainViewModelFactory(Injection.provideRepository(LocalContext.current))
    )
    val isLogin = if (mainViewModel.isLogin.collectAsState().value.isEmpty()) Screen.Login.route else Screen.Home.route

    mainViewModel.uiState.collectAsState(initial = UiState.Loading)
        .value.let {
            uiState ->
            when(uiState){
                is UiState.Loading ->{ mainViewModel.checkToken() }
                is UiState.Success ->{
                    navController.navigate(Screen.Home.route)
                }
                is UiState.Error ->{}
            }
        }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 60.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .padding(20.dp)
                .clickable{navController.navigate(isLogin)}
        )
        {
            Image(
                painter = painterResource(R.drawable.logo_plantcare) ,
                contentDescription = "",
                modifier = modifier
                    .height(400.dp)
                    .width(400.dp)
            )
            Text(
                text = "PlantCare",
                fontStyle = FontStyle(R.font.sora_regular),
                fontSize = 52.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .fillMaxWidth(1f)
            )
        }
    }
}

