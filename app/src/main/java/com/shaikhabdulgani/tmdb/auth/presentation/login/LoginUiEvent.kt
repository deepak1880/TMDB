package com.shaikhabdulgani.tmdb.auth.presentation.login

sealed interface LoginUiEvent {
    data object LoginClick : LoginUiEvent
    data class OnEmailChange(val email: String) : LoginUiEvent
    data class OnPasswordChange(val password: String) : LoginUiEvent
}