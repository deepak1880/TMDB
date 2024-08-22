package com.shaikhabdulgani.tmdb.moviedetail.data.source.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.shaikhabdulgani.tmdb.moviedetail.data.source.local.entity.MovieDetailEntity

@Dao
interface MovieDetailDao {

    @Query("SELECT * FROM moviedetailentity WHERE id=:id")
    fun getMovieDetail(id: Int): MovieDetailEntity?

    @Upsert
    fun insert(movieDetailEntity: MovieDetailEntity)

}