package com.shaikhabdulgani.tmdb.moviedetail.data.source.remote.dto


import com.google.gson.annotations.SerializedName

data class VideoResult(
    @SerializedName("results")
    val results: List<VideoDto>
)