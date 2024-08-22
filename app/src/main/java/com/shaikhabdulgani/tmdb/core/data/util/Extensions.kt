package com.shaikhabdulgani.tmdb.core.data.util

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun <T> Task<T>.await() = suspendCancellableCoroutine { continuation ->
    addOnCompleteListener { task ->
        if (task.isSuccessful) {
            continuation.resume(task)
        } else {
            continuation.resumeWithException(task.exception ?: Exception("Unknown error"))
        }
    }

    continuation.invokeOnCancellation {
        continuation.cancel()
    }
}

fun getRank(page: Int, pageSize: Int, index: Int): Int {
    return (page - 1) * pageSize + index
}

fun isOneHourEarlier(epochMillis: Long): Boolean {
    val currentTimeMillis = System.currentTimeMillis()
    val oneHourMillis = 60 * 60 * 1000
    return (currentTimeMillis - epochMillis) >= oneHourMillis
}