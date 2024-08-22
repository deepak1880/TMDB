package com.shaikhabdulgani.tmdb.core.domain.repository

import com.shaikhabdulgani.tmdb.core.domain.model.User
import com.shaikhabdulgani.tmdb.core.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUser(): Flow<Resource<User>>
    suspend fun addUser(username: String, email: String): Flow<Resource<User>>
}