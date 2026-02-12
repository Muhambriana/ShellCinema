package com.mshell.shellcinema.core.data

import com.mshell.shellcinema.core.data.source.Resource
import com.mshell.shellcinema.core.data.source.remote.ApiResponse
import com.mshell.shellcinema.core.data.source.remote.RemoteDataSource
import com.mshell.shellcinema.core.domain.model.Genre
import com.mshell.shellcinema.core.domain.model.Genres
import com.mshell.shellcinema.core.domain.repository.ShellCinemaRepository
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

}