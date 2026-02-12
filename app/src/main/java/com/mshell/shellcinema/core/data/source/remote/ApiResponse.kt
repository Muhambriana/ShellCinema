package com.mshell.shellcinema.core.data.source.remote


sealed class ApiResponse<out R> {
    data class Progress(val progress: Int) : ApiResponse<Nothing>()
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val errorMessage: String) : ApiResponse<Nothing>()
    data object Empty : ApiResponse<Nothing>()
}