package com.mshell.shellcinema.core.data.source.remote

import com.mshell.shellcinema.core.domain.model.Genres
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("genre/movie/list/language=en")
    suspend fun getGenres(
        @Query("language") language: String
    ):Genres
}