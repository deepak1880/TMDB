package com.shaikhabdulgani.tmdb.moviedetail.data.source.remote.dto


import com.google.gson.annotations.SerializedName

data class CreditsDto(
    @SerializedName("cast")
    val cast: List<CastDto>,
    @SerializedName("crew")
    val crew: List<CrewDto>
)