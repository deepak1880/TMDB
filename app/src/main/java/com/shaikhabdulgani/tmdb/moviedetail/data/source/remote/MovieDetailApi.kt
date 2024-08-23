package com.shaikhabdulgani.tmdb.moviedetail.data.source.remote

import com.shaikhabdulgani.tmdb.moviedetail.data.source.remote.dto.MovieDetailDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailApi {

    @GET("{media_type}/{id}")
    suspend fun getMovieDetail(
        @Path("id") id: Int,
        @Path("media_type") mediaType: String,
        @Query("append_to_response") append_to_response: String = "videos,credits,recommendations"
    ): MovieDetailDto

//    @GET("movie/{id}/credits")
//    suspend fun getCreditsList(
//        @Path("id") id: Int
//    ): GetCreditsResponse
//
//    @GET("movie/{id}/recommendations")
//    suspend fun getRecommendations(
//        @Path("id") id: Int
//    ): GetRecommendationsResponse

}