package com.shaikhabdulgani.tmdb.search.data.repository

import com.shaikhabdulgani.tmdb.base.BaseRepository
import com.shaikhabdulgani.tmdb.core.data.util.Result
import com.shaikhabdulgani.tmdb.search.data.mapper.toMovieSearchResult
import com.shaikhabdulgani.tmdb.search.data.mapper.toPersonSearchResult
import com.shaikhabdulgani.tmdb.search.data.mapper.toSearchResult
import com.shaikhabdulgani.tmdb.search.data.mapper.toSeriesSearchResult
import com.shaikhabdulgani.tmdb.search.data.remote.SearchApi
import com.shaikhabdulgani.tmdb.search.domain.model.SearchResult
import com.shaikhabdulgani.tmdb.search.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val api: SearchApi
): SearchRepository, BaseRepository() {
    override suspend fun searchAll(query: String, page: Int): Result<List<SearchResult>> {
        return execute {
            val result = api.searchAll(query,page).results
            result.map { it.toSearchResult() }
        }
    }

    override suspend fun searchMovie(query: String, page: Int): Result<List<SearchResult>> {
        return execute {
            val result = api.searchMovie(query,page).results
            result.map { it.toMovieSearchResult() }
        }
    }

    override suspend fun searchTv(query: String, page: Int): Result<List<SearchResult>> {
        return execute {
            val result = api.searchTv(query,page).results
            result.map { it.toSeriesSearchResult() }
        }
    }

    override suspend fun searchPerson(query: String, page: Int): Result<List<SearchResult>> {
        return execute {
            val result = api.searchPerson(query,page).results
            result.map { it.toPersonSearchResult() }
        }
    }
}