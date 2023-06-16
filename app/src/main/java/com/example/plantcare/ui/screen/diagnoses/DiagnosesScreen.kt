package com.example.plantcare.ui.screen.diagnoses

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.plantcare.ui.common.UiState
import com.example.plantcare.ui.navigation.Screen
import com.example.plantcare.ui.screen.detail.DetailContent
import com.example.plantcare.ui.theme.PlantCareTheme

@Composable
fun DiagnosesScreen (
    modifier: Modifier = Modifier,
    navController: NavController
){
    DiagnosesContent(
        nameDisiase = "Busuk Daun",
        causeBy = "Phytophtheora infestans",
        description = "Cendawan ini akan menimbulkan bercak kecoklatan pada area daun lalu sekitarnya menjadi kuning dan akhirnya sebagian beasar atau bahkan seluruh daun menjadi busuk.",
        recomendation = "Mulailah dengan merawat tanaman yang terinfeksi, berhati-hatilah membuang daun, batang, atau buah yang sakit.",
        navController =  (navController),
    )
}


@Composable
fun DiagnosesContent(
    nameDisiase : String,
    causeBy : String,
    description : String,
    recomendation : String,
    navController: NavController,
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
                    onClick = { navController.navigate(Screen.Home.route) },
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
                    text = "Diagnosis Tanaman",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(end = 32.dp)
                        .align(Alignment.CenterVertically)
                        .fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(100.dp))
            Box(modifier = modifier
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .fillMaxWidth()
                .fillMaxHeight(1f)
                .background(color = MaterialTheme.colors.onPrimary)
            ) {
                Column {
                    Text(
                        text = nameDisiase,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = modifier
                            .padding(top = 12.dp, start = 12.dp)
                    )
                    Text(
                        text = causeBy,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray,
                        fontSize = 18.sp,
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
                    Text(
                        text = "Cara Mencegah",
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(top = 12.dp, start = 12.dp)
                    )
                    Text(
                        text = recomendation,
                        fontWeight = FontWeight.Normal,
                        maxLines = 10,
                        modifier = Modifier
                            .padding(top = 12.dp, start = 12.dp)
                    )
                }
                Button(
                    onClick = {},
                    modifier = Modifier
                        .padding(bottom = 60.dp)
                        .align(BottomCenter)
                        .clip(RoundedCornerShape(size = 10.dp))
                ) {
                    Text(
                        text = "Rekomendasi Obat",
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DiagnosesPreview(){
    PlantCareTheme {

    }
}