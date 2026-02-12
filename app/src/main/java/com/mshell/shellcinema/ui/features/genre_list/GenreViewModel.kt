package com.mshell.shellcinema.ui.features.genre_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshell.shellcinema.core.data.source.Resource
import com.mshell.shellcinema.core.domain.model.Genre
import com.mshell.shellcinema.core.domain.repository.ShellCinemaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GenreViewModel(private val repository: ShellCinemaRepository): ViewModel() {
    private val _genreState = MutableStateFlow<Resource<List<Genre>?>>(Resource.Loading())
    val genreState: StateFlow<Resource<List<Genre>?>> = _genreState.asStateFlow()

    init {
        getGenres("en")
    }

    fun getGenres(language: String) {
        viewModelScope.launch {
            repository.getGenres(language).collect {
                _genreState.value = it
            }
        }
    }
}

