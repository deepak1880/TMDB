package com.shaikhabdulgani.tmdb.home.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.shaikhabdulgani.tmdb.home.data.local.entity.MovieEntity


@Dao
interface MovieDao {
    @Query("SELECT * FROM movieentity WHERE category=:category ORDER BY rank ASC LIMIT :limit OFFSET :offset")
    fun getMovies(category: String, limit: Int, offset: Int): List<MovieEntity>

    @Upsert
    fun insertAll(list: List<MovieEntity>)

    @Query("DELETE FROM movieentity")
    fun clear()
}