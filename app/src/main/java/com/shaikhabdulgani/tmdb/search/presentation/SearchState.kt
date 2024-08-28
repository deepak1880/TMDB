package com.shaikhabdulgani.tmdb.search.presentation

data class SearchState<out T>(
    val list: List<T> = emptyList(),
    val endReached: Boolean = false,
)