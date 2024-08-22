package com.shaikhabdulgani.tmdb.search.data.remote

import com.shaikhabdulgani.tmdb.search.data.remote.dto.SearchResultResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("search/multi")
    suspend fun searchAll(
        @Query("query") query: String,
        @Query("page") page: Int,
    ): SearchResultResponseDto

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("page") page: Int,
    ): SearchResultResponseDto

    @GET("search/tv")
    suspend fun searchTv(
        @Query("query") query: String,
        @Query("page") page: Int,
    ): SearchResultResponseDto

    @GET("search/person")
    suspend fun searchPerson(
        @Query("query") query: String,
        @Query("page") page: Int,
    ): SearchResultResponseDto

}