package com.shaikhabdulgani.tmdb.core.data.repository

import com.shaikhabdulgani.tmdb.base.BaseRepository
import com.shaikhabdulgani.tmdb.core.domain.model.User
import com.shaikhabdulgani.tmdb.core.domain.repository.UserRepository
import com.shaikhabdulgani.tmdb.core.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(): UserRepository, BaseRepository() {
    override suspend fun getUser(): Flow<Resource<User>> {
        return executeWithFlow {
            User(
                uid = "",
                username =  "",
                email = "",
                favorites = arrayOf("adas")
            )
        }
    }

    override suspend fun addUser(username: String, email: String): Flow<Resource<User>> {
        return executeWithFlow {
            User(
                uid = "",
                username =  "",
                email = "",
                favorites = arrayOf("adas")
            )
        }
    }
}