package com.shaikhabdulgani.tmdb.home.data.remote.model

data class GetSeriesListResponseModel(
    val page: Int,
    val results: List<SeriesDto>
)