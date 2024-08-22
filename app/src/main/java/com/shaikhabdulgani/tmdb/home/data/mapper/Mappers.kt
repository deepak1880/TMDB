package com.shaikhabdulgani.tmdb.home.data.mapper

import com.shaikhabdulgani.tmdb.home.data.remote.model.MovieDto
import com.shaikhabdulgani.tmdb.home.data.remote.model.SeriesDto
import com.shaikhabdulgani.tmdb.home.data.local.entity.MovieEntity
import com.shaikhabdulgani.tmdb.home.data.local.entity.SeriesEntity
import com.shaikhabdulgani.tmdb.home.domain.model.Movie

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

fun MovieEntity.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        imageId = imageId
    )
}

fun SeriesEntity.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        imageId = imageId
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