package com.shaikhabdulgani.tmdb.search.data.remote.dto


import com.google.gson.annotations.SerializedName

data class SearchResultResponseDto(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<SearchResultDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)