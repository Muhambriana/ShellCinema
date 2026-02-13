package com.mshell.shellcinema.core.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mshell.shellcinema.core.data.source.remote.RemoteDataSource
import com.mshell.shellcinema.core.domain.model.Review

class ReviewPagingSource(
    private val remoteDataSource: RemoteDataSource,
    private val movieId: Int?
) : PagingSource<Int, Review>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        val page = params.key ?: 1

        return try {
            val response = remoteDataSource.getMovieReviewsForPaging(movieId, page)
            val reviews = response.results?.filterNotNull() ?: emptyList()

            LoadResult.Page(
                data = reviews,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (reviews.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Review>): Int? = null
}