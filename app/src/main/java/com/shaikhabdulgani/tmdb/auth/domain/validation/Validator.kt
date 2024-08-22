package com.shaikhabdulgani.tmdb.auth.domain.validation

interface Validator<in T> {
    fun validate(data: T): ValidationResult
}
