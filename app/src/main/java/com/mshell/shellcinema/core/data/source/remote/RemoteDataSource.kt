package com.mshell.shellcinema.core.data.source.remote

import android.util.Log
import com.mshell.shellcinema.core.domain.model.DiscoverMovies
import com.mshell.shellcinema.core.domain.model.Genre
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val api: Api) {
    fun getGenres(language: String): Flow<ApiResponse<List<Genre>?>> {
        return flow {
            try {
                val response = api.getGenres(language)
                if (response.genres == null) {
                    emit(ApiResponse.Error("Failed to fetch data"))
                    return@flow
                }

                val data = response.genres
                emit(
                    ApiResponse.Success(data)
                )
            } catch (e: Exception) {
                emit(ApiResponse.Error("Oops.. Something went wrong"))
                e.printStackTrace()
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMoviesForPaging(
        genreId: Int?,
        page: Int
    ): DiscoverMovies {
        return api.getMoviesByGenre(genreId.toString(), page)
    }
}