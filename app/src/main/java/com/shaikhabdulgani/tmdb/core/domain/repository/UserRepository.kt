package com.shaikhabdulgani.tmdb.core.domain.repository

import com.shaikhabdulgani.tmdb.core.data.util.Result
import com.shaikhabdulgani.tmdb.core.domain.model.User

interface UserRepository {
    suspend fun getUser(forceRemoteFetch: Boolean, uid: String): Result<User>
    suspend fun addUser(uid: String, username: String, email: String): Result<User>
    suspend fun bookmarkMovie(uid: String,movieId: String): Result<Boolean>
    suspend fun removeMovieBookmark(uid: String,movieId: String): Result<Boolean>
    suspend fun isBookmarked(uid: String, movieId: String): Result<Boolean>
}