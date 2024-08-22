package com.shaikhabdulgani.tmdb.moviedetail.domain.model

import com.shaikhabdulgani.tmdb.home.domain.model.Movie

data class MovieDetail(
    val id: Int,
    val backdropPath: String,
    val posterPath: String,
    val title: String,
    val type: String,
    val description: String,
    val runtime: String,
    val releaseYear: String,
    val rating: Double,
    val genres: List<Genre>,
    val cast: List<Cast>,
    val recommendations: List<Movie>
)

