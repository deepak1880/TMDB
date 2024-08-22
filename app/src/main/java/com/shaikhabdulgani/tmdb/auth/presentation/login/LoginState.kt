package com.shaikhabdulgani.tmdb.auth.presentation.login

data class LoginState(
    val email: String = "",
    val emailError: String = "",
    val password: String = "",
    val passwordError: String = "",
)