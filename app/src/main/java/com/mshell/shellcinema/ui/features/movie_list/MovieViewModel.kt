package com.mshell.shellcinema.ui.features.movie_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mshell.shellcinema.core.domain.model.Genre
import com.mshell.shellcinema.core.domain.model.Movie
import com.mshell.shellcinema.core.domain.repository.ShellCinemaRepository
import com.mshell.shellcinema.utils.Config
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class MovieViewModel(
    private val repository: ShellCinemaRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val genre = savedStateHandle.toRoute<Genre>()
    val genreName = genre.name

    @OptIn(ExperimentalCoroutinesApi::class)
    val movies: Flow<PagingData<Movie>> = savedStateHandle
        .getStateFlow(Config.GENRE_ID, genre.id)
        .flatMapLatest { id ->
            repository.getMoviesByGenre(id)
        }
        .cachedIn(viewModelScope)
}

