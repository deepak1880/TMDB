package com.shaikhabdulgani.tmdb.home.data.mapper

import com.shaikhabdulgani.tmdb.home.data.remote.model.MovieDto
import com.shaikhabdulgani.tmdb.home.data.remote.model.SeriesDto
import com.shaikhabdulgani.tmdb.home.data.local.entity.MovieEntity
import com.shaikhabdulgani.tmdb.home.data.local.entity.SeriesEntity
import com.shaikhabdulgani.tmdb.home.domain.model.Media
import com.shaikhabdulgani.tmdb.search.domain.model.MediaType

fun MovieDto.toEntity(category: String, rank: Int): MovieEntity {
    return MovieEntity(
        id = id,
        timestamp = System.currentTimeMillis(),
        category = category,
        title = title,
        imageId = posterPath ?: "",
        rank = rank
    )
}

fun MovieEntity.toMovie(): Media {
    return Media(
        id = id,
        title = title,
        imageId = imageId,
        mediaType = MediaType.MOVIE
    )
}

fun SeriesEntity.toMovie(): Media {
    return Media(
        id = id,
        title = title,
        imageId = imageId,
        mediaType = MediaType.SERIES
    )
}

fun SeriesDto.toEntity(category: String, rank: Int): SeriesEntity {
    return SeriesEntity(
        id = id,
        timestamp = System.currentTimeMillis(),
        category = category,
        title = originalName,
        imageId = posterPath ?: "",
        rank = rank
    )
}