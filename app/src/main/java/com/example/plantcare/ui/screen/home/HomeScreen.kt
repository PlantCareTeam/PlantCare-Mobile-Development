package com.example.plantcare.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plantcare.R
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.plantcare.di.Injection
import com.example.plantcare.model.categories.Category
import com.example.plantcare.model.histories.History
import com.example.plantcare.model.user.DataItem
import com.example.plantcare.model.user.LoginRequest
import com.example.plantcare.model.user.UserModel
import com.example.plantcare.ui.ViewModelFactory
import com.example.plantcare.ui.common.UiState
import com.example.plantcare.ui.components.CategoryItems
import com.example.plantcare.ui.components.EmptyContent
import com.example.plantcare.ui.components.HistoryItems
import com.example.plantcare.ui.navigation.Screen
import com.example.plantcare.ui.screen.MainViewModelFactory
import com.example.plantcare.ui.screen.auth.AuthViewModel
import com.example.plantcare.ui.theme.PlantCareTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    navigateToDetail: (Int) -> Unit,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    mainViewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
){
    val name = remember {
        mutableStateOf("")
    }

    val userData  = DataItem(name = name.value)

    viewModel.uiState.collectAsState(initial = UiState.Loading)
        .value.let {
            uiState->
            when(uiState){
                is UiState.Loading -> {
                    mainViewModel.getUser()
                    viewModel.getAllCategory()
                }
                is UiState.Success -> {
                    HomeContent(
                        banner = R.drawable.banner_1,
                        listCategory = uiState.data,
                        modifier = modifier,
                        userData = userData,
                        navigateToDetail = navigateToDetail,
                        listHistory = emptyList()
                    )
                }
                is UiState.Error -> {}
            }
        }
}

@Composable
fun HomeContent(
    banner : Int,
    userData : DataItem,
    listCategory: List<Category>,
    listHistory: List<History>,
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
    navigateToDetail: (Int) -> Unit,
){
    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ){
        Column(
            modifier = modifier
        ) {
            Text(
                text = "Hi ${userData.name}!",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(start = 12.dp, top = 16.dp)
            )
            Text(
                text = "Selamat datang di PlantCare",
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 12.dp)
            )
            Image(
                painter = painterResource(banner),
                contentDescription = null,
                modifier = Modifier
                    .padding(bottom = 12.dp, end = 12.dp, start = 12.dp, top = 12.dp)
                    .fillMaxWidth()
                    .height(192.dp)
            )
            Box(
                modifier = modifier
            ){
                Column {
                    Text(
                        text = "Kategori",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(top = 12.dp, start = 12.dp)
                    )
                    Spacer(modifier = modifier.height(4.dp))
                    LazyRow(
                        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = modifier
                    ) {
                        items(listCategory, key = { it.id }) { item ->
                            CategoryItems(
                                photo = item.photo,
                                name = item.name,
                                modifier = modifier
                                    .clickable {
                                        navigateToDetail(item.id)
                                    }
                            )
                        }
                    }
                }
            }
            HomeHistoryContent(listHistory = listHistory)

        }
    }
}

@Composable
fun HomeHistoryContent(
    listHistory: List<History>,
    modifier: Modifier = Modifier,
){
    Box(
        modifier = modifier
    ){
        Text(
            text = "History",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(top = 12.dp, start = 12.dp)
        )
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


@Preview(showBackground = true)
@Composable
fun HomeContentPreview(
) {
    PlantCareTheme {
        HomeContent(
            banner = R.drawable.banner_1,
            listCategory = emptyList(),
            navigateToDetail = { },
            userData = DataItem(),
            listHistory = emptyList()
        )
    }
}