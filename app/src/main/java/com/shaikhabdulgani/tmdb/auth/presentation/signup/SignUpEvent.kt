package com.shaikhabdulgani.tmdb.auth.presentation.signup

sealed interface SignUpEvent {
    data object SignUpClick : SignUpEvent
    data class OnEmailChange(val email: String) : SignUpEvent
    data class OnUsernameChange(val username: String) : SignUpEvent
    data class OnPasswordChange(val password: String) : SignUpEvent
    data class OnRePasswordChange(val password: String) : SignUpEvent
}