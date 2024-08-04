package com.ibrahimcanerdogan.turkiyebankdatabase.ui.navigation

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ibrahimcanerdogan.turkiyebankdatabase.ui.view.detail.DetailScreen
import com.ibrahimcanerdogan.turkiyebankdatabase.ui.view.home.HomeScreen
import com.ibrahimcanerdogan.turkiyebankdatabase.ui.view.splash.SplashScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.SPLASH_SCREEN.name
    ) {
        composable(
            route = AppScreen.SPLASH_SCREEN.name,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(500, easing = FastOutLinearInEasing)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -1000 },
                    animationSpec = tween(500, easing = FastOutLinearInEasing)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -1000 },
                    animationSpec = tween(500, easing = FastOutLinearInEasing)
                )
            }
        ) {
            SplashScreen(navController = navController)
        }
        composable(
            route = AppScreen.HOME_SCREEN.name,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(500, easing = FastOutLinearInEasing)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -1000 },
                    animationSpec = tween(500, easing = FastOutLinearInEasing)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -1000 },
                    animationSpec = tween(500, easing = FastOutLinearInEasing)
                )
            }
        ) {
            HomeScreen(navController = navController)
        }
        composable(
            route = "${AppScreen.DETAIL_SCREEN.name}/{bankDataJson}",
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(500, easing = FastOutLinearInEasing)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -1000 },
                    animationSpec = tween(500, easing = FastOutLinearInEasing)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -1000 },
                    animationSpec = tween(500, easing = FastOutLinearInEasing)
                )
            },
            arguments = listOf(navArgument("bankDataJson") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val bankDataJson = backStackEntry.arguments?.getString("bankDataJson")
            if (bankDataJson != null) {
                DetailScreen(bankDataJson)
            }
        }
    }
}