package com.shaikhabdulgani.tmdb.home.domain.model

import com.shaikhabdulgani.tmdb.search.domain.model.MediaType

data class Media(
    val id: Int,
    val title: String,
    val imageId: String,
    val mediaType: MediaType
)