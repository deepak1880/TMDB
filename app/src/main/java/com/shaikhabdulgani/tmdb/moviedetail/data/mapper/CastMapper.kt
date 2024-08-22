package com.shaikhabdulgani.tmdb.moviedetail.data.mapper

import com.shaikhabdulgani.tmdb.moviedetail.data.source.local.entity.CastEntity
import com.shaikhabdulgani.tmdb.moviedetail.data.source.remote.dto.CastDto
import com.shaikhabdulgani.tmdb.moviedetail.domain.model.Cast


//fun CastDto.toCastEntity(): CastEntity {
//    return CastEntity(
//        id = id,
//        name = name,
//        imageId = profilePath ?: "",
//    )
//}
//
//fun CastEntity.toCast(): Cast {
//    return Cast(
//        id = id,
//        name = name,
//        imageId = imageId,
//    )
//}
fun CastDto.toCastEntity(): CastEntity {
    return CastEntity(
        id = id,
        name = name,
        imageId = profilePath ?: "",
    )
}

fun CastEntity.toCast(): Cast {
    return Cast(
        id = id,
        name = name,
        imageId = imageId,
    )
}