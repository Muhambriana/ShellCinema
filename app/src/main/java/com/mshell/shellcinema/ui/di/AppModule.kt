package com.mshell.shellcinema.ui.di

import com.mshell.shellcinema.ui.features.genre_list.GenreViewModel
import com.mshell.shellcinema.ui.features.main.MainViewModel
import com.mshell.shellcinema.ui.features.movie_list.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { GenreViewModel(get()) }
    viewModel { MovieViewModel(get(),get()) }
}