package com.shaikhabdulgani.tmdb.auth.domain.repository

import com.shaikhabdulgani.tmdb.auth.domain.model.LoginResult
import com.shaikhabdulgani.tmdb.core.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(
        email: String,
        password: String
    ): Flow<Resource<LoginResult>>

    suspend fun signUp(
        email: String,
        username: String,
        password: String
    ): Flow<Resource<LoginResult>>
}