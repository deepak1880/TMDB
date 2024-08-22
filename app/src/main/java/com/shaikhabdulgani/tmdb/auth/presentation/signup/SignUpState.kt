package com.shaikhabdulgani.tmdb.auth.presentation.signup

data class SignUpState(
    val email: String = "",
    val emailError: String = "",
    val username: String = "",
    val usernameError: String = "",
    val password: String = "",
    val passwordError: String = "",
    val repeatPassword: String = "",
    val repeatPasswordError: String = "",
)