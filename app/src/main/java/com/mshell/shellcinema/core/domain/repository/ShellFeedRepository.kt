package com.mshell.shellcinema.core.domain.repository

import com.mshell.shellcinema.core.data.source.Resource
import com.mshell.shellcinema.core.domain.model.Genre
import com.mshell.shellcinema.core.domain.model.Genres
import kotlinx.coroutines.flow.Flow

interface ShellCinemaRepository {
    fun getGenres(language: String): Flow<Resource<List<Genre>?>>
}