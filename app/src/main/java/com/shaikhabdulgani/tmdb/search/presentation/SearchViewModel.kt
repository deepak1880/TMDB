package com.shaikhabdulgani.tmdb.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shaikhabdulgani.tmdb.core.data.util.Result
import com.shaikhabdulgani.tmdb.core.presentation.util.Paginator
import com.shaikhabdulgani.tmdb.search.domain.model.SearchResult
import com.shaikhabdulgani.tmdb.search.domain.repository.SearchRepository
import com.shaikhabdulgani.tmdb.search.presentation.util.SearchFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _query: MutableStateFlow<QueryState> =
        MutableStateFlow(QueryState())
    val query: StateFlow<QueryState> = _query

    private val _result: MutableStateFlow<List<SearchResult>> =
        MutableStateFlow(listOf())

    private var isFetching = false

    val result: StateFlow<List<SearchResult>> = query
        .debounce(1000)
        .combine(_result) { queryState, result ->
            when (queryState.event) {
                QueryEvent.Page -> {
                    val res = paginatedResult.getNext().data
                    if (res == null) {
                        result
                    } else {
                        res + result
                    }

                }

                QueryEvent.Query -> {
                    paginatedResult.reset()
                    if (queryState.query.isEmpty()) {
                        return@combine emptyList()
                    }
                    val res = paginatedResult.getNext().data ?: emptyList()
                    res
                }
            }
//            search(queryState, 1, result)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private val paginatedResult = Paginator<SearchResult>(
        initialPage = 1,
        onLoading = {},
        onRequest = {
            println("Fetching page $it")
//            if (isFetching) {
//                isFetching = true
                search(queryState = query.value, it)
//                isFetching = false
//            }
//            Result.success(emptyList())
        },
        onComplete = {},
        onClear = {}
    )

    private suspend fun search(
        queryState: QueryState,
        page: Int,
    ): Result<List<SearchResult>> {
        val result = viewModelScope.async {
            when (queryState.filter) {
                SearchFilter.ALL -> {
                    searchRepository
                        .searchAll(
                            query = queryState.query,
                            page = page
                        )
                }

                SearchFilter.MOVIE -> {
                    searchRepository
                        .searchMovie(
                            query = queryState.query,
                            page = page
                        )
                }

                SearchFilter.SERIES -> {
                    searchRepository
                        .searchTv(
                            query = queryState.query,
                            page = page
                        )
                }

                SearchFilter.PERSON -> {
                    searchRepository
                        .searchPerson(
                            query = queryState.query,
                            page = page
                        )
                }
            }
        }.await()
        return result
    }

//    private suspend fun search(
//        queryState: QueryState,
//        page: Int,
//        prevResult: List<SearchResult>
//    ): List<SearchResult> {
//        val result = viewModelScope.async {
//            when (queryState.filter) {
//                SearchFilter.ALL -> {
//                    searchRepository
//                        .searchAll(
//                            query = queryState.query,
//                            page = page
//                        ).data ?: prevResult
//                }
//
//                SearchFilter.MOVIE -> {
//                    searchRepository
//                        .searchMovie(
//                            query = queryState.query,
//                            page = page
//                        ).data ?: prevResult
//                }
//
//                SearchFilter.SERIES -> {
//                    searchRepository
//                        .searchTv(
//                            query = queryState.query,
//                            page = page
//                        ).data ?: prevResult
//                }
//
//                SearchFilter.PERSON -> {
//                    searchRepository
//                        .searchPerson(
//                            query = queryState.query,
//                            page = page
//                        ).data ?: prevResult
//                }
//            }
//        }.await()
//        return result
//    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.QueryChange -> onQueryChange(event.query)
            is SearchEvent.FilerChange -> onFilerChange(event.searchFilter)
            is SearchEvent.ReachedEnd -> onReachedEnd()
        }
    }

    private fun onReachedEnd() {
        _query.value = _query.value.copy(event = QueryEvent.Page)
    }

    private fun onQueryChange(query: String) {
        _query.value = _query.value.copy(query = query, event = QueryEvent.Query)
    }

    private fun onFilerChange(filter: SearchFilter) {
        _query.value = _query.value.copy(filter = filter, event = QueryEvent.Query)
    }
}

