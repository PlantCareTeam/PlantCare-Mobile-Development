package com.example.plantcare.ui.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.testTag
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
import com.example.plantcare.ui.ViewModelFactory
import com.example.plantcare.ui.common.UiState
import com.example.plantcare.ui.navigation.Screen
import com.example.plantcare.ui.screen.home.HomeViewModel
import com.example.plantcare.ui.theme.PlantCareTheme

@Composable
fun DetailScreen(
    categoryId: Int,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getCategoryById(categoryId)
            }
            is UiState.Success -> {
                val data = uiState.data
                val category = if (data.id == 1) Screen.Scan.route else Screen.ScanApple.route
                DetailContent(
                    photo = data.photo,
                    name = data.name,
                    scienceName = data.scienceName,
                    description = data.description,
                    navigateBack = navigateBack,
                    navigateToScan = { navController.navigate(category) },
                    modifier = modifier
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    photo : Int,
    name : String,
    scienceName : String,
    description : String,
    navigateBack: () -> Unit,
    navigateToScan: () -> Unit,
    modifier: Modifier = Modifier,
){
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.primary)
    ) {
        Column(
            modifier = modifier
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .background(color = Color.White)
            ){
                IconButton(
                    onClick = navigateBack,
                    modifier = Modifier
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
                    text = "Detail Tanaman",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(end = 32.dp)
                        .align(Alignment.CenterVertically)
                        .fillMaxWidth()
                )
            }
            Image(
                painter = painterResource(photo),
                contentDescription = name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = modifier
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = MaterialTheme.colors.onPrimary)
                .verticalScroll(rememberScrollState())
            ) {
                Column {
                    Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        modifier = modifier
                            .padding(top = 12.dp, start = 12.dp)
                    )
                    Text(
                        text = scienceName,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(top = 12.dp, start = 12.dp)
                    )
                    Text(
                        text = description,
                        fontWeight = FontWeight.Normal,
                        maxLines = 10,
                        modifier = Modifier
                            .padding(top = 12.dp, start = 12.dp)
                    )
                    Spacer(modifier = Modifier.padding(top = 40.dp))
                    Button(
                        onClick = {navigateToScan()},
                        modifier = modifier
                            .fillMaxWidth(0.6f)
                            .align(CenterHorizontally)
                            .padding(bottom = 60.dp)
                            .clickable { navigateToScan }
                    ) {
                        Text(text = "Scan Here")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailContentPreview() {
    PlantCareTheme {
        DetailContent(
            photo = R.drawable.noto_tomato,
            name = "Tomat",
            scienceName = "Solanum lycopersicum",
            description = "Tomat atau rangam (Solanum lycopersicum) adalah tumbuhan dari keluarga Solanaceae, tumbuhan asli Amerika Tengah dan Selatan, dari Meksiko sampai Peru.",
            navigateBack = {},
            navigateToScan = {},
            modifier = Modifier
        )
    }
}