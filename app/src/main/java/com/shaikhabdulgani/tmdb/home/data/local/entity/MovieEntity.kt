package com.shaikhabdulgani.tmdb.home.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    primaryKeys = ["id","rank","category"]
)
data class MovieEntity(
    val id: Int,
    val timestamp: Long,
    val category: String,
    val title: String,
    val imageId: String,
    val rank: Int,
)