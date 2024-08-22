package com.shaikhabdulgani.tmdb.auth.data.source.remote.dto

import com.shaikhabdulgani.tmdb.core.domain.model.User

data class User(
    val uid: String,
    val username: String,
    val email: String,
    val favorites: Array<String>,
){
    fun mapToDomainUser(): User{
        return User(
            uid = uid,
            username = username,
            email = email,
            favorites = favorites,
        )
    }
}