package com.shaikhabdulgani.tmdb.search.data.remote

import com.shaikhabdulgani.tmdb.search.data.remote.dto.SearchResultResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchApi {

    @GET("search/{searchType}")
    suspend fun search(
        @Path("searchType") searchType: String,
        @Query("query") query: String,
        @Query("page") page: Int,
    ): SearchResultResponseDto

}