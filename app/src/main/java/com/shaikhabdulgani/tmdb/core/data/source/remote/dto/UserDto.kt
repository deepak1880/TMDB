package com.shaikhabdulgani.tmdb.core.data.source.remote.dto

data class UserDto(
    val uid: String = "",
    val username: String = "",
    val email: String = "",
    val favorites: List<String> = emptyList()
)