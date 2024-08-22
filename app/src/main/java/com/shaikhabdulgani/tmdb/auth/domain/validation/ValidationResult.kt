package com.shaikhabdulgani.tmdb.auth.domain.validation

data class ValidationResult(
    val isValid: Boolean,
    val error: String = "",
)