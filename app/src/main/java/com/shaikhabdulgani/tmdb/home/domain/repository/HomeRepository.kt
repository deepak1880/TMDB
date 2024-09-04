package com.shaikhabdulgani.tmdb.home.domain.repository

import com.shaikhabdulgani.tmdb.core.data.util.Result
import com.shaikhabdulgani.tmdb.home.domain.model.Media

interface HomeRepository {
    suspend fun getTrendingMovies(page: Int): Result<List<Media>>
    suspend fun getUpcomingMovies(page: Int): Result<List<Media>>
    suspend fun getPopularSeries(page: Int): Result<List<Media>>
    suspend fun getOnTheAirSeries(page: Int): Result<List<Media>>
}