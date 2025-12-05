package dev.jmyp.movies.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.jmyp.movies.ui.screens.allCredits.AllCreditsScreen
import dev.jmyp.movies.ui.screens.allMovies.AllMoviesScreen
import dev.jmyp.movies.ui.screens.home.HomeScreen
import dev.jmyp.movies.ui.screens.movieDetail.MovieDetailScreen
import dev.jmyp.movies.ui.screens.personDetail.PersonDetailScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Route.Home) {
        composable<Route.Home> {
            HomeScreen(navController = navController)
        }
        composable<Route.MovieDetail> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.MovieDetail>()
            MovieDetailScreen(navController = navController, args = args)
        }
        composable<Route.PersonDetail> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.PersonDetail>()
            PersonDetailScreen(navController = navController, args = args)
        }
        composable<Route.AllMovies> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.AllMovies>()
            AllMoviesScreen(navController = navController, args = args)
        }
        composable<Route.AllCredits> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.AllCredits>()
            AllCreditsScreen(navController = navController, args = args)
        }
    }
}