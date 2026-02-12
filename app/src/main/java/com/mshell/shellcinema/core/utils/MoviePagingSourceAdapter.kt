package com.mshell.shellcinema.core.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mshell.shellcinema.core.data.source.remote.RemoteDataSource
import com.mshell.shellcinema.core.domain.model.Movie

class MoviePagingSource(
    private val remoteDataSource: RemoteDataSource,
    private val genreId: Int?
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1

        return try {
            // 1. Call the API directly (suspending)
            val response = remoteDataSource.getMoviesForPaging(genreId, page)
            val movies = response.results?.filterNotNull() ?: emptyList()

            // 2. Return Success (Paging 3's version of ApiResponse.Success)
            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (movies.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            // 3. Return Error (Paging 3's version of ApiResponse.Error)
            // This will trigger LoadState.Error in your Compose UI
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? = null
}