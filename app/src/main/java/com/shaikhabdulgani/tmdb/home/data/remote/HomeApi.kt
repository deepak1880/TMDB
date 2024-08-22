package com.shaikhabdulgani.tmdb.home.data.remote

import com.shaikhabdulgani.tmdb.home.data.remote.model.GetMovieListResponseModel
import com.shaikhabdulgani.tmdb.home.data.remote.model.GetSeriesListResponseModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeApi {
    @GET("movie/{category}")
    suspend fun getMovies(
        @Path("category") category: String,
        @Query("page") page: Int,
    ): GetMovieListResponseModel

    @GET("tv/{category}")
    suspend fun getSeries(
        @Path("category") category: String,
        @Query("page") page: Int,
    ): GetSeriesListResponseModel
}