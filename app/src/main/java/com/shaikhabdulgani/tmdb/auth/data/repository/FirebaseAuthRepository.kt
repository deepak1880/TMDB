package com.shaikhabdulgani.tmdb.auth.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.shaikhabdulgani.tmdb.base.BaseRepository
import com.shaikhabdulgani.tmdb.core.data.util.await
import com.shaikhabdulgani.tmdb.auth.domain.model.LoginResult
import com.shaikhabdulgani.tmdb.auth.domain.repository.AuthRepository
import com.shaikhabdulgani.tmdb.core.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
) : AuthRepository, BaseRepository() {
    override suspend fun login(email: String, password: String): Flow<Resource<LoginResult>> {
        return executeWithFlow {
            val result = auth.signInWithEmailAndPassword(email, password).await()

            if (!result.isSuccessful || result.result == null || result.result.user == null) {
                //unsuccessful case
                if (result.exception != null && result.exception!!.message != null) {
                    throw Exception(result.exception!!.message!!)
                } else {
                    // unknown unsuccessful case
                    throw Exception("Some error occurred")
                }
            }

            //success case
            return@executeWithFlow LoginResult(
                result.result!!.user!!.uid
            )
        }
    }

    override suspend fun signUp(
        email: String,
        username: String,
        password: String
    ): Flow<Resource<LoginResult>> {
        return executeWithFlow {
            val result = auth.createUserWithEmailAndPassword(email, password).await()

            //success case
            if (result.isSuccessful && result.result != null && result.result.user != null) {
                return@executeWithFlow LoginResult(result.result!!.user!!.uid)
            }

            //unsuccessful case
            if (result.exception != null && result.exception!!.message != null) {
                throw Exception(result.exception!!.message!!)
            }

            // unknown unsuccessful case
            throw Exception("Some error occurred")
        }
    }
}