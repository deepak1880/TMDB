package com.shaikhabdulgani.tmdb.auth.presentation

data class InputTextState(
    val value: String = "",
    val error: String = "",
){
    fun noError(value: String): InputTextState{
        return InputTextState(value = value)
    }
}
