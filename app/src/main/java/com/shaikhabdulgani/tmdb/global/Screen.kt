package com.shaikhabdulgani.tmdb.global

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
    @Serializable
    data object Onboarding : Screen

    @Serializable
    data object Home : Screen

    @Serializable
    data object Login : Screen

    @Serializable
    data object SignUp : Screen

    @Serializable
    data object Search : Screen

    @Serializable
    class MovieDetail(val id: Int) : Screen
}