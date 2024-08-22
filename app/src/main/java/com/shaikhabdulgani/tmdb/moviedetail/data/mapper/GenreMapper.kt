package com.shaikhabdulgani.tmdb.moviedetail.data.mapper

import com.shaikhabdulgani.tmdb.moviedetail.data.source.local.entity.GenreEntity
import com.shaikhabdulgani.tmdb.moviedetail.data.source.remote.dto.GenreDto
import com.shaikhabdulgani.tmdb.moviedetail.domain.model.Genre

fun GenreEntity.toGenre(): Genre{
    return Genre(
        id = id,
        name = name
    )
}

fun GenreDto.toGenreEntity(): GenreEntity {
    return GenreEntity(
        id = id,
        name = name
    )
}