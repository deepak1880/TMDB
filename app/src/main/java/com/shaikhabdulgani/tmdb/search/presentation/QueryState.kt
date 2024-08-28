package com.shaikhabdulgani.tmdb.search.presentation

import com.shaikhabdulgani.tmdb.search.presentation.util.SearchFilter

data class QueryState(
    val query: String = "",
    val filter: SearchFilter = SearchFilter.ALL,
    val event: QueryEvent = QueryEvent.Query,
    val flip: Boolean = false
)

sealed interface QueryEvent {
    data object Query : QueryEvent
    data object Page : QueryEvent
}