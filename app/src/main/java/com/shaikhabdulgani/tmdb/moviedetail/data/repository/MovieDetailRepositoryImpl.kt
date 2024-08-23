package com.shaikhabdulgani.tmdb.moviedetail.data.repository

import com.shaikhabdulgani.tmdb.base.BaseRepository
import com.shaikhabdulgani.tmdb.core.domain.util.Resource
import com.shaikhabdulgani.tmdb.moviedetail.data.source.local.MovieDetailDao
import com.shaikhabdulgani.tmdb.moviedetail.data.mapper.toMovieDetail
import com.shaikhabdulgani.tmdb.moviedetail.data.mapper.toMovieDetailEntity
import com.shaikhabdulgani.tmdb.moviedetail.data.source.remote.MovieDetailApi
import com.shaikhabdulgani.tmdb.moviedetail.domain.model.MovieDetail
import com.shaikhabdulgani.tmdb.moviedetail.domain.repository.MovieDetailRepository
import com.shaikhabdulgani.tmdb.search.domain.model.MediaType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(
    private val movieDetailApi: MovieDetailApi,
    private val movieDetailDao: MovieDetailDao
) : BaseRepository(), MovieDetailRepository {

    override suspend fun getMovieDetail(
        id: Int,
        mediaType: MediaType
    ): Flow<Resource<MovieDetail>> {
        return executeWithFlow {

            val result = movieDetailDao.getMovieDetail(id = id,mediaType = mediaType.getValue())
            if (result != null) {
                return@executeWithFlow result.toMovieDetail()
            }

            val localDb = movieDetailApi.getMovieDetail(id = id,mediaType = mediaType.getValue()).toMovieDetailEntity(mediaType.getValue())
            movieDetailDao.insert(localDb)

            return@executeWithFlow localDb.toMovieDetail()
        }
    }
}