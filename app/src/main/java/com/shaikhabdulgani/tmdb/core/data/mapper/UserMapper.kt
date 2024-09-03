package com.shaikhabdulgani.tmdb.core.data.mapper

import com.google.firebase.firestore.DocumentSnapshot
import com.shaikhabdulgani.tmdb.auth.data.util.USER_COLLECTION
import com.shaikhabdulgani.tmdb.core.data.source.local.entity.UserEntity
import com.shaikhabdulgani.tmdb.core.data.source.remote.FirebaseConstants
import com.shaikhabdulgani.tmdb.core.data.source.remote.dto.UserDto
import com.shaikhabdulgani.tmdb.core.domain.model.User

fun UserDto.toUserEntity(): UserEntity {
    return UserEntity(
        uid = uid,
        username = username,
        email = email,
        favorites = favorites
    )
}

fun UserEntity.toUser(): User {
    return User(
        uid = uid,
        username = username,
        email = email,
        favorites = favorites
    )
}

fun DocumentSnapshot.mapToUserDto(): UserDto {
    val uid = getString(FirebaseConstants.USER_UID) ?: ""
    val username = getString(FirebaseConstants.USER_USERNAME) ?: ""
    val email = getString(FirebaseConstants.USER_EMAIL) ?: ""
    val favorites = get(FirebaseConstants.USER_FAVORITES) as? List<String> ?: emptyList()
    return UserDto(
        uid = uid,
        username = username,
        email = email,
        favorites = favorites
    )
}