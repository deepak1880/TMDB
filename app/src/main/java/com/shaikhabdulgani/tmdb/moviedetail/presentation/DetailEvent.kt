package com.shaikhabdulgani.tmdb.moviedetail.presentation

sealed interface DetailEvent {
    data object BookmarkClick : DetailEvent
}