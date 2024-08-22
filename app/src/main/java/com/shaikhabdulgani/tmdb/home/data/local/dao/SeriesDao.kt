package com.shaikhabdulgani.tmdb.home.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.shaikhabdulgani.tmdb.home.data.local.entity.SeriesEntity


@Dao
interface SeriesDao {
    @Query("SELECT * FROM seriesentity WHERE category=:category ORDER BY rank ASC LIMIT :limit OFFSET :offset")
    fun getSeries(category: String, limit: Int, offset: Int): List<SeriesEntity>

    @Upsert
    fun insertAll(list: List<SeriesEntity>)

    @Query("DELETE FROM seriesentity")
    fun clear()
}