package com.shaikhabdulgani.tmdb.moviedetail.data.source.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.shaikhabdulgani.tmdb.moviedetail.data.source.local.entity.MovieDetailEntity
import com.shaikhabdulgani.tmdb.search.domain.model.MediaType

@Dao
interface MovieDetailDao {

    @Query("SELECT * FROM moviedetailentity WHERE id=:id AND type=:mediaType")
    fun getMovieDetail(id: Int, mediaType: String): MovieDetailEntity?

    @Upsert
    fun insert(movieDetailEntity: MovieDetailEntity)

}