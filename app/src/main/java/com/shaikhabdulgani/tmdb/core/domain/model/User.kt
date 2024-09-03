package com.shaikhabdulgani.tmdb.core.domain.model

data class User(
    val uid: String,
    val username: String,
    val email: String,
    val favorites: List<String>,
)