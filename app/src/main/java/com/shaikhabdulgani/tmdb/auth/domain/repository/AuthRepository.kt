package com.shaikhabdulgani.tmdb.auth.domain.repository

import com.shaikhabdulgani.tmdb.core.domain.model.User
import com.shaikhabdulgani.tmdb.core.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(
        email: String,
        password: String
    ): Flow<Resource<User>>

    suspend fun signUp(
        email: String,
        username: String,
        password: String
    ): Flow<Resource<User>>

    fun isLoggedIn(): Boolean

    suspend fun uuid(): String

    suspend fun getLoggedInUser(): User?

    suspend fun logout()
}