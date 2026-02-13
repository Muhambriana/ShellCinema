package com.mshell.shellcinema.ui.features.movie_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mshell.shellcinema.core.domain.model.Movie
import com.mshell.shellcinema.core.domain.repository.ShellCinemaRepository
import com.mshell.shellcinema.ui.navigation.Screen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class MovieViewModel(
    private val repository: ShellCinemaRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    val genreId: Int = savedStateHandle.get<Int>(Screen.GENRE_ID) ?: 0
    val genreName: String = savedStateHandle.get<String>(Screen.GENRE_NAME) ?: "Unknown"

    @OptIn(ExperimentalCoroutinesApi::class)
    val movies: Flow<PagingData<Movie>> = savedStateHandle
        .getStateFlow(Screen.GENRE_ID, genreId)
        .flatMapLatest { id ->
            repository.getMoviesByGenre(id)
        }
        .cachedIn(viewModelScope)
}

