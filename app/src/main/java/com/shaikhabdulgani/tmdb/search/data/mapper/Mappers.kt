package com.shaikhabdulgani.tmdb.search.data.mapper

import com.shaikhabdulgani.tmdb.search.data.remote.dto.SearchResultDto
import com.shaikhabdulgani.tmdb.search.domain.model.ContentType
import com.shaikhabdulgani.tmdb.search.domain.model.SearchResult

fun SearchResultDto.toSearchResult(): SearchResult {
    val title = parseTitle()
    val type = ContentType.parse(this.mediaType)
    return SearchResult(
        id = id,
        title = title,
        imageId = posterPath ?: profilePath ?: "",
        type = type,
    )
}

fun SearchResultDto.toMovieSearchResult(): SearchResult {
    val title = parseTitle()
    return SearchResult(
        id = id,
        title = title,
        imageId = posterPath ?: "",
        type = ContentType.MOVIE,
    )
}

fun SearchResultDto.toSeriesSearchResult(): SearchResult {
    val title = parseTitle()
    return SearchResult(
        id = id,
        title = title,
        imageId = posterPath ?: "",
        type = ContentType.SERIES,
    )
}

fun SearchResultDto.toPersonSearchResult(): SearchResult {
    val title = parseTitle()
    return SearchResult(
        id = id,
        title = title,
        imageId = profilePath ?: "",
        type = ContentType.PERSON,
    )
}

private fun SearchResultDto.parseTitle(): String{
    return when {
        this.title != null -> title
        this.originalTitle != null -> originalTitle
        this.name != null -> name
        this.originalName != null -> originalName
        else -> "No Title"
    }
}