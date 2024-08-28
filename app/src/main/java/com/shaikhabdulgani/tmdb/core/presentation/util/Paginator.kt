package com.shaikhabdulgani.tmdb.core.presentation.util

import android.util.Log
import com.shaikhabdulgani.tmdb.core.data.util.Result

class Paginator<T> (
    private val initialPage: Int,
    private inline val onLoading: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: Int) -> Result<List<T>>,
    private inline val getNextPage: suspend (List<T>, Int) -> Int,
    private inline val onError: (suspend (String?) -> Unit)?,
    private inline val onSuccess: (suspend (items: List<T>, newPage: Int) -> Unit)?,
    private inline val onClear: (suspend () -> Unit),
) : IPaginator<T> {

    constructor(
        initialPage: Int,
        onLoading: (Boolean) -> Unit,
        onRequest: suspend (nextKey: Int) -> Result<List<T>>,
        onClear: suspend () -> Unit = {}
    ) : this(
        initialPage = initialPage,
        onLoading = onLoading,
        onRequest = onRequest,
        getNextPage = { list: List<T>, curr: Int -> curr + 1 },
        onError = null,
        onSuccess = null,
        onClear = onClear,
    )

//    constructor(
//        initialPage: Int,
//        onLoading: (Boolean) -> Unit,
//        onRequest: suspend (nextKey: Int) -> Result<List<T>>,
//        getNextPage: suspend (List<T>, Int) -> Int,
//        onError: (suspend (String?) -> Unit)? = null,
//        onSuccess: suspend (items: List<T>, newPage: Int) -> Unit,
//        onClear: suspend () -> Unit = {},
//    ) : this(
//        initialPage = initialPage,
//        onLoading = onLoading,
//        onRequest = onRequest,
//        getNextPage = getNextPage,
//        onError = onError,
//        onSuccess = onSuccess,
//        onClear = onClear,
//    )

    private var isMakingRequest = false
    private var currentPage: Int = initialPage

    override suspend fun loadNext() {
        if (isMakingRequest) return
        isMakingRequest = true
        onLoading(true)
        val result = onRequest(currentPage)
        isMakingRequest = false


        when (result) {
            is Result.Failure -> {
                onError?.invoke(result.error)
                onLoading(false)
            }

            is Result.Success -> {
                currentPage = getNextPage(result.data!!, currentPage)
                onSuccess?.invoke(result.data, currentPage)
                onLoading(false)
            }
        }
    }

    suspend fun getNext(): Result<List<T>> {
        Log.d("Paginator::getNext","Fetching page $currentPage")
        onLoading(true)
        val result = onRequest(currentPage)
        when {
            result is Result.Success -> currentPage = getNextPage(result.data!!, currentPage)
        }
        onLoading(false)
        return result
    }

    override suspend fun reset() {
        currentPage = initialPage
        onClear.invoke()
    }
}