package com.example.plantcare.ui.screen.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.plantcare.di.Injection
import com.example.plantcare.model.histories.History
import com.example.plantcare.ui.ViewModelFactory
import com.example.plantcare.ui.components.EmptyContent
import com.example.plantcare.ui.components.HistoryItems
import com.example.plantcare.ui.screen.MainViewModelFactory
import com.example.plantcare.ui.screen.home.HomeViewModel
import com.example.plantcare.ui.screen.home.MainViewModel

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    mainViewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
){
    HistoryContent(listHistory = emptyList())
}


@Composable
fun HistoryContent(
    listHistory: List<History>,
    modifier: Modifier = Modifier,
){
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(72.dp)
                .background(color = Color.White)
        ){
            Text(
                text = "History",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(end = 32.dp)
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth(1f)
            )
        }
        Box(
            modifier = modifier
        ){
            if(listHistory.isEmpty()){
                EmptyContent(text = "Belum Ada Riwayat Diagnosis")
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = modifier
                        .padding(start = 12.dp)
                ){
                    items(listHistory, key = {it.id}){
                            item ->
                        HistoryItems(
                            photo = item.photo,
                            nameDisease = item.disease,
                            causeBy = item.caused,
                            category = item.category ,
                            date = item.date
                        )
                    }
                }
            }
        }
    }

}

