package com.shaikhabdulgani.tmdb.auth.domain.validation

data class AuthValidators(
    val emailValidator: EmailValidator,
    val passwordValidator: PasswordValidator,
    val rePasswordValidator: RepeatPasswordValidator,
    val usernameValidator: UsernameValidator,
)
