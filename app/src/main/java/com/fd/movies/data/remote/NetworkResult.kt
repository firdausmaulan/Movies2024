package com.fd.movies.data.remote

sealed class NetworkResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : NetworkResult<T>()
    data class Error(val exception: Throwable) : NetworkResult<Nothing>()
}