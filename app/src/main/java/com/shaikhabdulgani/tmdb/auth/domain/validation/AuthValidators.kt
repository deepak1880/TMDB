package com.shaikhabdulgani.tmdb.auth.domain.validation

data class AuthValidators(
    val emailValidator: Validator<String>,
    val passwordValidator: Validator<String>,
    val rePasswordValidator: Validator<Pair<String,String>>,
    val usernameValidator: Validator<String>,
)
