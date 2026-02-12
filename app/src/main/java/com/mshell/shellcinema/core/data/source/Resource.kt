package com.mshell.shellcinema.core.data.source

sealed class Resource<T>(val data: T? = null, val errorMessage: String? = null, val progress: Int? = null) {
    class Loading<T>(data: T? = null, progress: Int? = null) : Resource<T>(data, progress = progress)
    class Error<T>(data: T? = null, errorMessage: String? = null) : Resource<T>(data, errorMessage)
    class Success<T>(data: T) : Resource<T>(data)
}