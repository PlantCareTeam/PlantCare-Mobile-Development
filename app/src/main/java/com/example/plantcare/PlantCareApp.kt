package com.example.plantcare

import android.window.SplashScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.plantcare.di.Injection
import com.example.plantcare.ui.common.UiState
import com.example.plantcare.ui.navigation.NavigationItems
import com.example.plantcare.ui.navigation.Screen
import com.example.plantcare.ui.screen.MainViewModelFactory
import com.example.plantcare.ui.screen.auth.LoginScreen
import com.example.plantcare.ui.screen.auth.RegisterScreen
import com.example.plantcare.ui.screen.camera.AppPermission
import com.example.plantcare.ui.screen.camera.CameraScreen
import com.example.plantcare.ui.screen.detail.DetailScreen
import com.example.plantcare.ui.screen.diagnoses.DiagnosesPreview
import com.example.plantcare.ui.screen.diagnoses.DiagnosesScreen
import com.example.plantcare.ui.screen.history.HistoryScreen
import com.example.plantcare.ui.screen.home.HomeScreen
import com.example.plantcare.ui.screen.home.MainViewModel
import com.example.plantcare.ui.screen.profile.PreviewProfileScreen
import com.example.plantcare.ui.screen.profile.ProfileContent
import com.example.plantcare.ui.screen.profile.ProfileScreen
import com.example.plantcare.ui.screen.scanTomato.ScanAppleScreen
import com.example.plantcare.ui.screen.scanTomato.ScanScreen
import com.example.plantcare.ui.screen.splash.SplashScreen
import com.example.plantcare.ui.theme.PlantCareTheme

@Composable
fun PlantCareApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val mainViewModel : MainViewModel = viewModel(
        factory = MainViewModelFactory(Injection.provideRepository(LocalContext.current))
    )
    val isLogin = if (mainViewModel.isLogin.collectAsState().value.isEmpty()) Screen.Login.route else Screen.Home.route
//    mainViewModel.uiState.collectAsState(initial = UiState.Loading)
//        .value.let {
//            uiState ->
//            when(uiState){
//                is UiState.Loading ->{ mainViewModel.checkToken() }
//                is UiState.Success ->{
//                    navController.navigate(Screen.Home.route)
//                }
//                is UiState.Error ->{}
//            }
//        }


    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Detail.route
                && currentRoute != Screen.Scan.route
                && currentRoute != Screen.Camera.route
                && currentRoute != Screen.Login.route
                && currentRoute != Screen.Splash.route
                && currentRoute != Screen.Register.route
                && currentRoute != Screen.Diagnoses.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
               HomeScreen(
                   navigateToDetail = { categoryId ->
                       navController.navigate(Screen.Detail.createRoute(categoryId))
                   },
                   navController = navController
               )
            }
            composable(Screen.Login.route) {
                LoginScreen(navController = navController)
            }
            composable(Screen.Register.route) {
                RegisterScreen(navController = navController)
            }
            composable(Screen.Profile.route) {
                PreviewProfileScreen()
            }
            composable(Screen.Camera.route) {
                CameraScreen(onTakePicture = { navController.navigateUp() })
            }
            composable(Screen.Diagnoses.route) {
                DiagnosesScreen(navController = navController)
            }
            composable(Screen.Splash.route) {
                SplashScreen(navController = navController)
            }
            composable(Screen.ScanApple.route) {
                ScanAppleScreen(navigateBack = { }, navController = navController)
            }
            composable(Screen.Permission.route){
                AppPermission {}
            }
            composable(Screen.History.route){
                HistoryScreen ()
            }
            composable(Screen.Scan.route) {
                ScanScreen(
                    navigateBack = {
                       navController.navigateUp()
                    },
                    navController = navController
                )
            }

            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("categoryId") { type = NavType.IntType }),
            ) {
                val id = it.arguments?.getInt("categoryId") ?: -1
                DetailScreen(
                    categoryId = id,
                    navigateBack = {
                        navController.navigateUp()
                    },
                    navController = navController,
                )
            }
        }

    }
}


@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    BottomNavigation(modifier = modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItems(
                title = stringResource(R.string.home),
                icon = Icons.Default.Home,
                screen = Screen.Home,
            ),
            NavigationItems(
                title = "History",
                icon = Icons.Default.List,
                screen = Screen.History,
            ),
            NavigationItems(
                title = stringResource(R.string.about_page),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile,
            ),
        )
        BottomNavigation {
            navigationItems.map { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = null
                        )
                    },
                    label = { Text(item.title) },
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlantCarePreview() {
    PlantCareTheme {
        PlantCareApp()
    }
}