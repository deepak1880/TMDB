package com.shaikhabdulgani.tmdb.moviedetail.data.mapper

import com.shaikhabdulgani.tmdb.home.domain.model.Movie
import com.shaikhabdulgani.tmdb.moviedetail.data.source.local.entity.RecommendationMovieEntity
import com.shaikhabdulgani.tmdb.moviedetail.data.source.remote.dto.RecommendationDto

fun RecommendationDto.toRecommendationMovieEntity(): RecommendationMovieEntity {
    return RecommendationMovieEntity(
        id = id,
        title = originalTitle,
        imageId = posterPath,
    )
}

fun RecommendationMovieEntity.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        imageId = imageId ?: "",
    )
}