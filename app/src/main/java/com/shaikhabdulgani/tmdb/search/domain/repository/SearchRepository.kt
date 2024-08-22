package com.shaikhabdulgani.tmdb.search.domain.repository

import com.shaikhabdulgani.tmdb.core.data.util.Result
import com.shaikhabdulgani.tmdb.search.domain.model.SearchResult


interface SearchRepository {
    suspend fun searchAll(query: String, page: Int): Result<List<SearchResult>>
    suspend fun searchMovie(query: String, page: Int): Result<List<SearchResult>>
    suspend fun searchTv(query: String, page: Int): Result<List<SearchResult>>
    suspend fun searchPerson(query: String, page: Int): Result<List<SearchResult>>
}