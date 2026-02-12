package com.mshell.shellcinema.ui.di

import com.mshell.shellcinema.ui.features.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}