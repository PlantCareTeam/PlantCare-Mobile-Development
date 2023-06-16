package com.example.plantcare.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.plantcare.R
import com.example.plantcare.di.Injection
import com.example.plantcare.model.user.DataItem
import com.example.plantcare.model.user.UserResponse
import com.example.plantcare.ui.common.UiState
import com.example.plantcare.ui.screen.MainViewModelFactory
import com.example.plantcare.ui.screen.home.MainViewModel
import com.example.plantcare.ui.theme.PlantCareTheme
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ProfileScreen(
    userStateFlow: StateFlow<UiState<DataItem>>,
    onLogout: () -> Unit,
    navController: NavController,
    mainViewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
    modifier : Modifier = Modifier
){
    mainViewModel.uiState.collectAsState(initial = UiState.Loading).value.let {
        uiState ->
        when (uiState){
            is UiState.Loading -> { mainViewModel.getUser()}
            is UiState.Success ->{
                ProfileContent(
                    name = uiState.data.name.toString(),
                    email = uiState.data.email.toString(),
                    role = uiState.data.role.toString(),
                    modifier = Modifier
                )
            }
            is UiState.Error ->{}
        }
    }
    userStateFlow.collectAsState(initial = UiState.Loading)
        .value.let {uiState ->

        }

}

@Composable
fun ProfileContent(
    name : String,
    email : String,
    role : String,
    modifier: Modifier = Modifier
){
    Box (
        modifier = modifier.fillMaxSize()
    ){
        Column(
            modifier = modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ){
            Box (
                modifier = Modifier
                    .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
                    .background(MaterialTheme.colors.primary)
            ) {
                Column {
                    Text(
                        text = "Profile",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .fillMaxWidth(1f)
                    )
                    Image(
                        painter = painterResource(R.drawable.profile),
                        contentDescription = "",
                        modifier = modifier
                            .padding(20.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .height(200.dp)
                            .width(200.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                }
            }

            Spacer(modifier = modifier.padding(top = 12.dp))
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(1f)
            )
            Spacer(modifier = modifier.padding(top = 12.dp))
            Text(
                text = email,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(1f)
            )
            Text(
                text = role,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(1f)
            )
        }
        Button(
            onClick = {

            },
            modifier = modifier
                .fillMaxWidth(0.4f)
                .padding(bottom = 60.dp)
                .align(Alignment.BottomCenter)
                .clickable { }
        ) {
            Text(text = "Logout")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    PlantCareTheme {
        ProfileContent(
            name = "Latifa Binti Cahyani",
            email = "latifaeyo@gmail.com",
            role = "Student",
            modifier = Modifier
        )
    }
}