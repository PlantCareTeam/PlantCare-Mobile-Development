package com.example.plantcare.ui.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.plantcare.R
import com.example.plantcare.di.Injection
import com.example.plantcare.model.user.RegisterRequest
import com.example.plantcare.ui.common.UiState
import com.example.plantcare.ui.screen.MainViewModelFactory
import kotlinx.coroutines.launch


@Composable
fun RegisterScreen(
    navController: NavController,
    registerViewModel: AuthViewModel = viewModel(
        factory = MainViewModelFactory(Injection.provideRepository(LocalContext.current))
    )
) {
    val name = remember {
        mutableStateOf("")
    }
    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    val passwordVisible = remember {
        mutableStateOf(false)
    }

    val coroutineScope = rememberCoroutineScope()

    val registerData : RegisterRequest = RegisterRequest(name = name.value, email = email.value, password = password.value)

    registerViewModel.uiStateRegis.collectAsState(initial = UiState.Loading).value.let {
            uiState ->
        when(uiState){
            is UiState.Loading -> {

            }
            is UiState.Success -> {
                navController.navigate("home")
            }
            is UiState.Error -> {}
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .padding(10.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.auth),
                contentDescription = null ,
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 32.dp)
                    .width(300.dp)
                    .height(300.dp)
            )
            Text(
                text = "Register",
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 40.sp
            )
            Spacer(modifier = Modifier.padding(20.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    label = { Text(text = "Enter your name") },
                    placeholder = { Text(text = "Enter your name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                OutlinedTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = { Text(text = "Email address") },
                    placeholder = { Text(text = "Email address") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f)
                )

                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_visibility),
                                contentDescription = "Password visibility",
                                tint = if (passwordVisible.value) Color.DarkGray else Color.Gray
                            )
                        }
                    },
                    label = { Text(text = "Password") },
                    placeholder = { Text(text = "Password") },
                    singleLine = true,
                    visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(0.8f)
                )

                Spacer(modifier = Modifier.padding(10.dp))
                Button(
                    onClick = {
                        registerViewModel.uiStateRegis.value
                        coroutineScope.launch {
                            registerViewModel.signUp(registerData){
                                navController.navigate("login")
                            }
                        }
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(size = 10.dp))
                        .fillMaxWidth(0.8f)
                        .height(50.dp),
                ) {
                    Text(
                        text = "Register"
                    )
                }

                Spacer(modifier = Modifier.padding(20.dp))
                Text(
                    text = "Already have an account? Login",
                    modifier = Modifier.clickable(onClick = {
                        navController.navigate("login") {
                            popUpTo = navController.graph.startDestinationId
                            launchSingleTop = true
                        }
                    })
                )
                Spacer(modifier = Modifier.padding(20.dp))
            }
        }
    }
}