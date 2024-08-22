package com.shaikhabdulgani.tmdb.core.data.remote.model

import com.shaikhabdulgani.tmdb.core.domain.model.User

data class UserDto(
    val uid: String,
    val username: String,
    val email: String,
    val favorites: Array<String>
)

fun UserDto.mapToDomain(): User {
    return User(
        uid = uid,
        username = username,
        email = email,
        favorites = favorites
    )
}