package com.mshell.shellcinema.core.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mshell.shellcinema.core.data.source.Resource
import com.mshell.shellcinema.core.data.source.remote.ApiResponse
import com.mshell.shellcinema.core.data.source.remote.RemoteDataSource
import com.mshell.shellcinema.core.domain.model.Genre
import com.mshell.shellcinema.core.domain.model.Movie
import com.mshell.shellcinema.core.domain.repository.ShellCinemaRepository
import com.mshell.shellcinema.core.utils.MoviePagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ShellCinemaRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : ShellCinemaRepository {
    override fun getGenres(language: String): Flow<Resource<List<Genre>?>> {
        return flow {
            remoteDataSource.getGenres(language).collect { apiResponse ->
                when (apiResponse) {
                    is ApiResponse.Success -> {
                        emit(Resource.Success(apiResponse.data))
                    }
                    is ApiResponse.Error -> {
                        emit(Resource.Error(errorMessage = apiResponse.errorMessage))
                    }
                    is ApiResponse.Progress -> {
                        emit(Resource.Loading(progress = apiResponse.progress))
                    }
                    is ApiResponse.Empty -> {
                        emit(Resource.Success(null))
                    }
                }
            }
        }
    }

    override fun getMoviesByGenre(genreId: Int?): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviePagingSource(remoteDataSource, genreId)
            }
        ).flow
    }


}