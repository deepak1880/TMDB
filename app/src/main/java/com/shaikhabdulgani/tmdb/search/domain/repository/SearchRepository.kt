package com.shaikhabdulgani.tmdb.search.domain.repository

import com.shaikhabdulgani.tmdb.core.data.util.Result
import com.shaikhabdulgani.tmdb.search.domain.model.SearchResult
import com.shaikhabdulgani.tmdb.search.domain.model.SearchType


interface SearchRepository {
    suspend fun search(query: String, page: Int, searchType: SearchType): Result<List<SearchResult>>
}