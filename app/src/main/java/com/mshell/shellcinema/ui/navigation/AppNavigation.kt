package com.mshell.shellcinema.ui.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mshell.shellcinema.core.domain.model.Genre
import com.mshell.shellcinema.ui.features.genre_list.GenreList
import com.mshell.shellcinema.ui.features.movie_list.MovieListScreen

@Composable
fun AppNavigation(
    navHostController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current

    NavHost(
        navController = navHostController,
        startDestination = Screen.GenreList.route
    ) {
        composable(Screen.GenreList.route) {
            GenreList(
                onItemClick = { genre ->
                    navHostController.currentBackStackEntry?.savedStateHandle?.set(
                        Screen.MOVIE_LIST_ARGUMENT,
                        genre
                    )
                    navHostController.navigate(Screen.MovieList.route)
                }
            )
        }

        composable(Screen.MovieList.route) {
            val genre = navHostController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Genre>(Screen.MOVIE_LIST_ARGUMENT)

            genre?.let { selectedGenre ->
                MovieListScreen(
                    onBackClick = {
                        navHostController.popBackStack()
                    },
                    onItemClick = {
                        navHostController.navigate()
                    }
                )
            }
        }
    }
}