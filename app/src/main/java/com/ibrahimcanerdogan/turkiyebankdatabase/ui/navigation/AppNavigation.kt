package com.ibrahimcanerdogan.turkiyebankdatabase.ui.navigation

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
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
            route = AppScreen.SPLASH_SCREEN.name
        ) {
            SplashScreen(navController = navController)
        }
        composable(
            route = AppScreen.HOME_SCREEN.name,
            enterTransition = {
                // https://developer.android.com/develop/ui/compose/animation/customize
                slideInHorizontally(
                    initialOffsetX = { -1000 },
                    animationSpec = tween(500, easing = LinearOutSlowInEasing)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -1000 },
                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                )
            }
        ) {
            HomeScreen(navController = navController)
        }
        composable(
            route = "${AppScreen.DETAIL_SCREEN.name}/{bankDataJson}",
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