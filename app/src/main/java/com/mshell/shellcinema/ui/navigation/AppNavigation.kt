package com.mshell.shellcinema.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mshell.shellcinema.ui.features.genre_list.GenreListScreen
import com.mshell.shellcinema.ui.features.movie_detail.MovieDetailScreen
import com.mshell.shellcinema.ui.features.movie_list.MovieListScreen

@Composable
fun AppNavigation(
    navHostController: NavHostController = rememberNavController()
) {

    NavHost(
        navController = navHostController,
        startDestination = Screen.GenreList.route
    ) {
        composable(Screen.GenreList.route) {
            GenreListScreen(
                onItemClick = { genre ->
                    val genreId = genre.id ?: return@GenreListScreen
                    val genreName = genre.name ?: "Unknown"
                    navHostController.navigate(Screen.MovieList.createRoute(genreId, genreName))
                }
            )
        }

        composable(
            route = Screen.MovieList.route,
            arguments = listOf(
                navArgument(Screen.GENRE_ID) { type = NavType.IntType },
                navArgument(Screen.GENRE_NAME) { type = NavType.StringType }
            )
        ) {
            MovieListScreen(
                onBackClick = {
                    navHostController.popBackStack()
                },
                onItemClick = { movie ->
                    val movieId = movie.id ?: return@MovieListScreen
                    navHostController.navigate(Screen.MovieDetail.createRoute(movieId))
                }
            )
        }

        composable(
            route = Screen.MovieDetail.route,
            arguments = listOf(
                navArgument(Screen.MOVIE_ID) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt(Screen.MOVIE_ID) ?: 0
            MovieDetailScreen(
                movieId = movieId,
                onBackClick = {
                    navHostController.popBackStack()
                }
            )
        }
    }
}