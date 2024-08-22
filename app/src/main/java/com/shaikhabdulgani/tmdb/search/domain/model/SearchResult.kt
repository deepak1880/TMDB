package com.shaikhabdulgani.tmdb.search.domain.model

data class SearchResult(
    val id: Int,
    val title: String,
    val imageId: String,
    val type: MediaType,
)