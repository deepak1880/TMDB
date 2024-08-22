package com.shaikhabdulgani.tmdb.core.data.remote

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.shaikhabdulgani.tmdb.core.data.remote.model.UserDto
import com.shaikhabdulgani.tmdb.core.data.remote.model.mapToDomain
import com.shaikhabdulgani.tmdb.core.data.util.Result
import com.shaikhabdulgani.tmdb.core.data.util.await
import com.shaikhabdulgani.tmdb.core.domain.model.User
import javax.inject.Inject

class UserFirestoreService @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun getUser(uid: String): Result<User> {
        try {
            val collection = firestore.collection(Constant.USER_COLLECTION)
            val result: DocumentSnapshot = collection.document(uid).get().await().result
            return Result.success(result.toObject(UserDto::class.java)!!.mapToDomain())
        } catch (e: Exception) {
            return Result.failure(e.message)
        }
    }

    suspend fun checkIfUsernameIsTaken(username: String): Result<Boolean> {
        try {
            val collection = firestore.collection(Constant.USER_COLLECTION)
            val query = collection
                .whereEqualTo(Constant.USER_USERNAME, username)
                .get()
                .await()
                .result
            return Result.success(false)
        } catch (e: Exception) {
            return Result.failure(e.message)
        }
    }

    suspend fun save(
        uid: String,
        username: String,
        email: String
    ): Result<User> {
        try {
            val collection = firestore.collection(Constant.USER_COLLECTION)
            val user = UserDto(
                uid = uid,
                username = username,
                email = email,
                favorites = emptyArray()
            )
            val result = collection.document(uid).set(user).await()
            if (!result.isSuccessful) {
                return Result.failure(result.exception?.message)
            }
            return Result.success(user.mapToDomain())

        } catch (e: Exception) {
            return Result.failure(e.message)
        }
    }
}