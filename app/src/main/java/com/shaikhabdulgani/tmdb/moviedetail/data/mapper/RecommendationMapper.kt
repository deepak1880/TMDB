package com.shaikhabdulgani.tmdb.moviedetail.data.mapper

import com.shaikhabdulgani.tmdb.home.domain.model.Media
import com.shaikhabdulgani.tmdb.moviedetail.data.source.local.entity.RecommendationMovieEntity
import com.shaikhabdulgani.tmdb.moviedetail.data.source.remote.dto.RecommendationDto
import com.shaikhabdulgani.tmdb.search.domain.model.MediaType

fun RecommendationDto.toRecommendationMovieEntity(): RecommendationMovieEntity {
    return RecommendationMovieEntity(
        id = id,
        title = originalTitle,
        imageId = posterPath,
    )
}

fun RecommendationMovieEntity.toMovie(): Media {
    return Media(
        id = id,
        title = title,
        imageId = imageId ?: "",
        mediaType = MediaType.MOVIE
    )
}