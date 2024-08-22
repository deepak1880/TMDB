package com.shaikhabdulgani.tmdb.moviedetail.data.source.remote.dto


import com.google.gson.annotations.SerializedName

data class RecommendationsResultDto(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<RecommendationDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)