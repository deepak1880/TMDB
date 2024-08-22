package com.shaikhabdulgani.tmdb.core.domain.util

sealed class Resource<out T>(val data: T? = null, val message: String? = null) {
    class Loading<T> : Resource<T>(data = null)
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    companion object {
        fun <T> loading(): Resource<T> {
            return Loading()
        }

        fun <T> success(data: T): Resource<T> {
            return Success(data)
        }

        fun <T> error(message: String, data: T? = null): Resource<T> {
            return Error(message, data)
        }
    }
}