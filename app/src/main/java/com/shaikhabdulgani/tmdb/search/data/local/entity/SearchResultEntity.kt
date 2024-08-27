package com.shaikhabdulgani.tmdb.search.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchResultEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val originalName: String,
    val title: String,
    val originalTitle: String,
)