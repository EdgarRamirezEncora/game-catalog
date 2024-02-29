package com.example.gamecatalogue.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gamecatalogue.ui.viewmodels.GamesViewModel
import com.example.gamecatalogue.ui.views.DetailView
import com.example.gamecatalogue.ui.views.HomeView
import com.example.gamecatalogue.ui.views.SearchGamesView

@Composable
fun NavManager(
    gamesViewModel: GamesViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home_view" ) {
        composable(route = "home_view") {
            HomeView(
                gamesViewModel = gamesViewModel,
                navController = navController
            )
        }

        composable(
            route = "detail_view/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
            ) {

            val gameId = it.arguments?.getInt("id") ?: 0

            DetailView(
                gamesViewModel = gamesViewModel,
                navController = navController,
                gameId = gameId
            )
        }

        composable(route = "search_games_view") {
            SearchGamesView(
                gamesViewModel = gamesViewModel,
                navController = navController
            )
        }
    }
}