package com.mshell.shellcinema.ui.navigation

sealed class Screen(val route: String) {
    data object GenreList: Screen("genre_list")
    data object MovieList: Screen("movie_list")

    companion object {
        const val MOVIE_LIST_ARGUMENT = "movie_list_argument"
    }
}