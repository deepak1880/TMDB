package com.shaikhabdulgani.tmdb.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.shaikhabdulgani.tmdb.core.data.source.local.UserConverter

@Entity
@TypeConverters(UserConverter::class)
data class UserEntity(
    @PrimaryKey
    val uid: String,
    val username: String,
    val email: String,
    val favorites: List<String>
)