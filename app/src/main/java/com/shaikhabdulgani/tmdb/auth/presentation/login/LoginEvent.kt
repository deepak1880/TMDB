package com.shaikhabdulgani.tmdb.auth.presentation.login

sealed interface LoginEvent {
    data object LoginClick : LoginEvent
    data class OnEmailChange(val email: String) : LoginEvent
    data class OnPasswordChange(val password: String) : LoginEvent
}