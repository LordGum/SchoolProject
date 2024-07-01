package com.example.schoolproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    enterScreenContent: @Composable () -> Unit,
    mainScreenContent: @Composable () -> Unit,
    detailScreenContent: @Composable (String) -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.MainScreen.route
    ) {
        composable(Screen.EnterScreen.route) {
            enterScreenContent()
        }
        composable(Screen.MainScreen.route) {
            mainScreenContent()
        }
        composable(
            route = Screen.DetailScreen.route,
            arguments = listOf(
                navArgument(Screen.ID) {
                    type = NavType.StringType
                }
            )
        ) {
            val id = it.arguments?.getString(Screen.ID) ?: "-1"
            detailScreenContent(id)
        }
    }
}