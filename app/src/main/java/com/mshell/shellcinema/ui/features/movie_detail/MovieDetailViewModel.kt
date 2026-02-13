package com.mshell.shellcinema.ui.features.movie_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mshell.shellcinema.core.data.source.Resource
import com.mshell.shellcinema.core.domain.model.MovieDetail
import com.mshell.shellcinema.core.domain.model.Review
import com.mshell.shellcinema.core.domain.repository.ShellCinemaRepository
import com.mshell.shellcinema.ui.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val repository: ShellCinemaRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId: Int = savedStateHandle.get<Int>(Screen.MOVIE_ID) ?: 0

    private val _movieState = MutableStateFlow<Resource<MovieDetail?>>(Resource.Loading())
    val movieState: StateFlow<Resource<MovieDetail?>> = _movieState.asStateFlow()

    private val _reviewsState = MutableStateFlow<PagingData<Review>>(PagingData.empty())
    val reviewsState: StateFlow<PagingData<Review>> = _reviewsState.asStateFlow()

    init {
        getMovieDetails(movieId)
    }

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
             repository.getMovieDetail(movieId).collect {
                 _movieState.value = it
             }
        }
    }

    fun loadReviews() {
        viewModelScope.launch {
            repository.getMovieReviews(movieId)
                .cachedIn(viewModelScope)
                .collect {
                    _reviewsState.value = it
                }
        }
    }
}
