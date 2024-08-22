package com.shaikhabdulgani.tmdb.core.data.util

sealed class Result<out T>(val data: T? = null, val error: String? = null) {
    class Success<T>(data: T?) : Result<T>(data = data)
    class Failure<T>(error: String?, data: T?) : Result<T>(error = error, data = data)

    companion object {
        fun <T> success(data: T): Result<T> {
            return Success(data)
        }

        fun <T> failure(error: String? = null, data: T? = null): Result<T> {
            return Failure(error = error, data = data)
        }
    }
}