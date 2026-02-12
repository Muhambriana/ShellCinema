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
import com.mshell.shellfeed.core.domain.model.NewsDetail
import com.mshell.shellfeed.ui.features.news_detail.NewsDetailScreen
import com.mshell.shellfeed.ui.features.news_list.NewsListScreen

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
            val newsDetail = navHostController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Genre>(Screen.Companion.MOVIE_LIST_ARGUMENT)

            newsDetail?.let { news ->
                MovieList(
                    newsDetail = news,
                    onBackClick = {
                        navHostController.popBackStack()
                    },
                    onShareClick = {
                        val shareIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "${news.title}\n\n${news.url}")
                            type = "text/plain"
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Share News"))
                    }
                )
            }
        }
    }
}