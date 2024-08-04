package com.ibrahimcanerdogan.turkiyebankdatabase.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ibrahimcanerdogan.turkiyebankdatabase.common.NetworkStatusChecker
import com.ibrahimcanerdogan.turkiyebankdatabase.ui.navigation.AppScreen
import com.ibrahimcanerdogan.turkiyebankdatabase.ui.theme.TurkiyeBankDatabaseTheme
import com.ibrahimcanerdogan.turkiyebankdatabase.ui.view.detail.DetailScreen
import com.ibrahimcanerdogan.turkiyebankdatabase.ui.view.home.HomeScreen
import com.ibrahimcanerdogan.turkiyebankdatabase.ui.view.splash.SplashScreen
import com.ibrahimcanerdogan.turkiyebankdatabase.util.NoInternetDialog
import dagger.hilt.android.AndroidEntryPoint

// Firebase Log sistemini ekle
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            //Internet connection check
            // -----------------------------------------------------------------------------------------------------------
            val context = LocalContext.current
            val internetConnectionFlow = NetworkStatusChecker.networkChecker(context)
            NoInternetDialog(internetConnectionFlow = internetConnectionFlow)
            //------------------------------------------------------------------------------------------------------------

            TurkiyeBankDatabaseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = AppScreen.SplashScreen.route
                    ) {
                        composable(route = AppScreen.SplashScreen.route,
                            enterTransition = {
                                slideInHorizontally(
                                    initialOffsetX = { 1000 },
                                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                                )
                            },
                            exitTransition = {
                                slideOutHorizontally(
                                    targetOffsetX = { -1000 },
                                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                                )
                            },
                            popEnterTransition = {
                                slideInHorizontally(
                                    initialOffsetX = { -1000 },
                                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                                )
                            }
                        ) {
                            SplashScreen(navController = navController)
                        }
                        composable(route = AppScreen.HomeScreen.route, enterTransition = {
                            slideInHorizontally(
                                initialOffsetX = { 1000 },
                                animationSpec = tween(500, easing = FastOutSlowInEasing)
                            )
                        },
                            exitTransition = {
                                slideOutHorizontally(
                                    targetOffsetX = { -1000 },
                                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                                )
                            },
                            popEnterTransition = {
                                slideInHorizontally(
                                    initialOffsetX = { -1000 },
                                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                                )
                            }) {
                            HomeScreen(navController = navController)
                        }
                        composable(
                            "${AppScreen.DetailScreen.route}/{bankDataJson}", enterTransition = {
                                slideInHorizontally(
                                    initialOffsetX = { 1000 },
                                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                                )
                            },
                            exitTransition = {
                                slideOutHorizontally(
                                    targetOffsetX = { -1000 },
                                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                                )
                            },
                            popEnterTransition = {
                                slideInHorizontally(
                                    initialOffsetX = { -1000 },
                                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                                )
                            },
                            arguments = listOf(navArgument("bankDataJson") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            val bankDataJson = backStackEntry.arguments?.getString("bankDataJson")
                            if (bankDataJson != null) {
                                DetailScreen(bankDataJson, navController)
                            }
                        }
                    }
                }
            }
        }
    }
}