package com.shaikhabdulgani.tmdb.search.presentation

data class SearchState<out T>(
    val list: List<T> = emptyList(),
    val isLoading: Boolean = false,
    val endReached: Boolean = false,
)