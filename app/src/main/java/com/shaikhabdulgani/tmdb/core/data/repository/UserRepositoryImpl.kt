package com.shaikhabdulgani.tmdb.core.data.repository

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.shaikhabdulgani.tmdb.base.BaseRepository
import com.shaikhabdulgani.tmdb.core.data.mapper.mapToUserDto
import com.shaikhabdulgani.tmdb.core.data.mapper.toUser
import com.shaikhabdulgani.tmdb.core.data.mapper.toUserEntity
import com.shaikhabdulgani.tmdb.core.data.source.local.AppDatabase
import com.shaikhabdulgani.tmdb.core.data.source.remote.FirebaseConstants
import com.shaikhabdulgani.tmdb.core.data.source.remote.dto.UserDto
import com.shaikhabdulgani.tmdb.core.data.util.Result
import com.shaikhabdulgani.tmdb.core.data.util.await
import com.shaikhabdulgani.tmdb.core.domain.model.User
import com.shaikhabdulgani.tmdb.core.domain.repository.UserRepository
import java.io.IOException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val db: AppDatabase
) : UserRepository, BaseRepository() {
    override suspend fun getUser(forceRemoteFetch: Boolean, uid: String): Result<User> {
        return execute {
            if (!forceRemoteFetch) {
                val user = db.userDao.getUser(uid)
                if (user != null) {
                    return@execute user.toUser()
                }
            }

            val collection = firestore.collection(FirebaseConstants.USER_COLLECTION)
            val result = collection.document(uid).get().await()

            if (!result.isSuccessful) {
                result.exception?.printStackTrace()
                throw IllegalStateException(result.exception?.message ?: "error fetching user")
            }

            if(!result.result.exists()){
                throw IllegalArgumentException("user not found with id $uid")
            }

            val userEntity = result.result.mapToUserDto().toUserEntity()
            db.userDao.saveUser(userEntity)
            userEntity.toUser()
        }
    }

    override suspend fun addUser(uid: String, username: String, email: String): Result<User> {
        return execute {
            val collection = firestore.collection(FirebaseConstants.USER_COLLECTION)
            val user = UserDto(
                uid = uid,
                username = username,
                email = email,
                favorites = emptyList()
            )
            val result = collection.document(uid).set(user).await()
            if (!result.isSuccessful) {
                throw Exception(result.exception?.message ?: "error saving user")
            }
            val userEntity = user.toUserEntity()
            db.userDao.saveUser(userEntity)
            userEntity.toUser()
        }
    }

    override suspend fun bookmarkMovie(uid: String, movieId: String): Result<Boolean> {
        return execute {
            val result = firestore
                .collection(FirebaseConstants.USER_COLLECTION)
                .document(uid)
                .update(
                    FirebaseConstants.USER_FAVORITES,
                    FieldValue.arrayUnion(movieId)
                ).await()

            result.isSuccessful
        }
    }

    override suspend fun removeMovieBookmark(uid: String, movieId: String): Result<Boolean> {
        return execute {
            val result = firestore
                .collection(FirebaseConstants.USER_COLLECTION)
                .document(uid)
                .update(
                    FirebaseConstants.USER_FAVORITES,
                    FieldValue.arrayRemove(movieId)
                ).await()

            result.isSuccessful
        }
    }

    override suspend fun isBookmarked(uid: String, movieId: String): Result<Boolean> {
        return execute {
            val user = getUser(true,uid)
            if(user is Result.Success){
                user.data?.favorites?.contains(movieId) ?: false
            }else{
                false
            }
        }
    }
}