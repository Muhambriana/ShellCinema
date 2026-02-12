package com.mshell.shellcinema.ui.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshell.shellcinema.core.data.source.Resource
import com.mshell.shellcinema.core.domain.model.Genre
import com.mshell.shellcinema.core.domain.model.Genres
import com.mshell.shellcinema.core.domain.repository.ShellCinemaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ShellCinemaRepository): ViewModel() {
    private val _genresState = MutableStateFlow<Resource<List<Genre>?>>(Resource.Loading())
    val genresState: StateFlow<Resource<List<Genre>?>> =_genresState.asStateFlow()

    init {
        getGenres("us")
    }

    fun getGenres(language: String) {
        viewModelScope.launch {
            repository.getGenres(language).collect {
                _genresState.value = it
            }
        }
    }
}