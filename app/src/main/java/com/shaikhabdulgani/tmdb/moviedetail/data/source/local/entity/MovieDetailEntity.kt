package com.shaikhabdulgani.tmdb.moviedetail.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieDetailEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val type: String,
    val description: String,
    val runtime: Int,
    val rating: Double,
    @ColumnInfo
    val recommendations: String,
    val cast: String,
    val genres: String,
    val releaseDate: String,
    val backdropPath: String,
    val posterPath: String
)