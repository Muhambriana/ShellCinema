package com.mshell.shellcinema.core.data.source.remote

import com.mshell.shellcinema.core.domain.model.DiscoverMovies
import com.mshell.shellcinema.core.domain.model.Genres
import com.mshell.shellcinema.core.domain.model.Reviews
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("language") language: String
    ):Genres

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("with_genres") genreId: String,
        @Query("page") page: Int,
    ): DiscoverMovies

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int?,
        @Query("page") page: Int
    ): Reviews
}