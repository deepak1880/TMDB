package com.shaikhabdulgani.tmdb.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shaikhabdulgani.tmdb.core.data.util.Result
import com.shaikhabdulgani.tmdb.core.presentation.util.Paginator
import com.shaikhabdulgani.tmdb.search.domain.model.SearchResult
import com.shaikhabdulgani.tmdb.search.domain.model.SearchType
import com.shaikhabdulgani.tmdb.search.domain.repository.SearchRepository
import com.shaikhabdulgani.tmdb.search.presentation.util.SearchFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _query: MutableStateFlow<QueryState> =
        MutableStateFlow(QueryState())
    val query: StateFlow<QueryState> = _query

    //        private val _result: MutableStateFlow<List<SearchResult>> =
//        MutableStateFlow(listOf())
    private val resultState: MutableStateFlow<SearchState<SearchResult>> =
        MutableStateFlow(SearchState())

    val result: StateFlow<SearchState<SearchResult>> = _query
        .debounce(1000)
        .map {
            val currentState = resultState.value
            when(it.event){
                QueryEvent.Page -> {
                    when(val res = paginatedResult.getNext()){
                        is Result.Failure -> {
                        }
                        is Result.Success -> {
                            resultState.value = currentState.copy(
                                list = currentState.list + res.data!!,
                                endReached = res.data.isEmpty(),
                            )
                        }
                    }
                }
                QueryEvent.Query -> {
                    paginatedResult.reset()
                    when(val res = paginatedResult.getNext()){
                        is Result.Failure -> {
                        }
                        is Result.Success -> {
                            resultState.value = currentState.copy(
                                list = res.data!!,
                                endReached = res.data.isEmpty(),
                            )
                        }
                    }
                }
            }
            resultState.value
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            SearchState()
        )

    private val paginatedResult: Paginator<SearchResult> = Paginator(
        initialPage = 1,
        onLoading = {},
        onRequest = {
            search(queryState = query.value, it)
        },
        onClear = {}
    )

    private suspend fun search(
        queryState: QueryState,
        page: Int,
    ): Result<List<SearchResult>> {
        val result = viewModelScope.async {
            val mediaType = when (queryState.filter) {
                SearchFilter.ALL -> SearchType.ALL
                SearchFilter.MOVIE -> SearchType.MOVIE
                SearchFilter.SERIES -> SearchType.SERIES
                SearchFilter.PERSON -> SearchType.PERSON
            }
            searchRepository.search(queryState.query, page, mediaType)
        }.await()
        return result
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.QueryChange -> onQueryChange(event.query)
            is SearchEvent.FilerChange -> onFilerChange(event.searchFilter)
            is SearchEvent.LoadMore -> onLoadMore()
        }
    }

    private fun onLoadMore() {
        _query.value = _query.value.copy(event = QueryEvent.Page)
    }

    private fun onQueryChange(query: String) {
        _query.value = _query.value.copy(query = query, event = QueryEvent.Query)
    }

    private fun onFilerChange(filter: SearchFilter) {
        _query.value = _query.value.copy(filter = filter, event = QueryEvent.Query)
    }
}

