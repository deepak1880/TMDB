package com.shaikhabdulgani.tmdb.home.data.remote.model

data class GetMovieListResponseModel(
    val page: Int,
    val results: List<MovieDto>
)