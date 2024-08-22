package com.shaikhabdulgani.tmdb.auth.presentation

sealed interface AuthState {
    data object Loading : AuthState
    data class Success(val uid: String) : AuthState
    data class Failure(val error: String) : AuthState
}