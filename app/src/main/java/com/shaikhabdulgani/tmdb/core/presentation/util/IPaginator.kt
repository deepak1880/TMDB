package com.shaikhabdulgani.tmdb.core.presentation.util

interface IPaginator<T> {
    suspend fun loadNext()
    suspend fun reset()
}