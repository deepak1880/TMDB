package com.shaikhabdulgani.tmdb.home.presentation

import com.shaikhabdulgani.tmdb.home.presentation.util.HomeTab

sealed interface HomeEvent {
    data class TabClicked(val selectedTab: HomeTab) : HomeEvent
}