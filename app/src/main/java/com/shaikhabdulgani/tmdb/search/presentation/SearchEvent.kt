package com.shaikhabdulgani.tmdb.search.presentation

import com.shaikhabdulgani.tmdb.search.presentation.util.SearchFilter

sealed interface SearchEvent{
    data class QueryChange(val query: String) : SearchEvent
    data class FilerChange(val searchFilter: SearchFilter) : SearchEvent
    data object LoadMore : SearchEvent
}