package com.shaikhabdulgani.tmdb.home.domain.repository

import com.shaikhabdulgani.tmdb.core.data.util.Result
import com.shaikhabdulgani.tmdb.home.domain.model.Movie

interface HomeRepository {
    suspend fun getTrendingMovies(page: Int): Result<List<Movie>>
    suspend fun getUpcomingMovies(page: Int): Result<List<Movie>>
    suspend fun getPopularSeries(page: Int): Result<List<Movie>>
    suspend fun getOnTheAirSeries(page: Int): Result<List<Movie>>
}