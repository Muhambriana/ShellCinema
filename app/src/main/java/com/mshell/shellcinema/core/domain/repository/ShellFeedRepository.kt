package com.mshell.shellcinema.core.domain.repository

import androidx.paging.PagingData
import com.mshell.shellcinema.core.data.source.Resource
import com.mshell.shellcinema.core.domain.model.Genre
import com.mshell.shellcinema.core.domain.model.Movie
import com.mshell.shellcinema.core.domain.model.MovieDetail
import com.mshell.shellcinema.core.domain.model.Review
import kotlinx.coroutines.flow.Flow

interface ShellCinemaRepository {
    fun getGenres(language: String): Flow<Resource<List<Genre>?>>
    fun getMoviesByGenre(genreId: Int?): Flow<PagingData<Movie>>
    fun getMovieReviews(movieId: Int?): Flow<PagingData<Review>>
    fun getMovieDetail(movieId: Int?): Flow<Resource<MovieDetail?>>
}