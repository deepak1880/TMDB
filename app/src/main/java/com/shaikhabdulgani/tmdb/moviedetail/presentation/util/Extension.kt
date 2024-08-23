package com.shaikhabdulgani.tmdb.moviedetail.presentation.util

import com.shaikhabdulgani.tmdb.moviedetail.domain.model.MovieDetail
import com.shaikhabdulgani.tmdb.search.domain.model.MediaType

fun emptyMovieDetail(): MovieDetail{
    return MovieDetail(
        0,
        "",
        "",
        "",
        MediaType.MOVIE,
        "",
        "",
        "",
        0.0,
        emptyList(),
        emptyList(),
        emptyList()
    )
}

fun Double.toSingleDecimalPlaces(): String {
    return String.format("%.1f", this)
}

fun parseReleaserYear(releaseDate: String): String {
    if (releaseDate.isNotBlank() && releaseDate.length >= 4) {
        return releaseDate.substring(0, 4)
    }
    return ""
}
