package com.shaikhabdulgani.tmdb.moviedetail.domain.repository

import com.shaikhabdulgani.tmdb.core.domain.util.Resource
import com.shaikhabdulgani.tmdb.moviedetail.domain.model.MovieDetail
import com.shaikhabdulgani.tmdb.search.domain.model.MediaType
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {
    suspend fun getMovieDetail(id: Int, mediaType: MediaType): Flow<Resource<MovieDetail>>
}