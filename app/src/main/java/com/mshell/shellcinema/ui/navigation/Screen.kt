package com.mshell.shellcinema.ui.navigation

sealed class Screen(val route: String) {
    data object GenreList: Screen("genre_list")
    data object MovieList: Screen("movie_list/{genreId}/{genreName}") {
        fun createRoute(genreId: Int, genreName: String) = "movie_list/$genreId/$genreName"
    }
    data object MovieDetail: Screen("movie_detail/{movieId}") {
        fun createRoute(movieId: Int) = "movie_detail/$movieId"
    }

    data object YouTubePlayerScreen: Screen("player/{videoKey}") {
        fun createRoute(videoKey: String) = "player/$videoKey"
    }

    companion object {
        const val GENRE_ID = "genreId"
        const val GENRE_NAME = "genreName"
        const val MOVIE_ID = "movieId"
        const val VIDEO_KEY = "videoKey"
    }
}