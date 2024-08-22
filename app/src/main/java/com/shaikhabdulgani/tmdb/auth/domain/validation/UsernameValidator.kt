package com.shaikhabdulgani.tmdb.auth.domain.validation

class UsernameValidator : Validator<String> {
    override fun validate(data: String): ValidationResult {
        val hasLengthGreaterThan4 = data.length > 4
        val containsWhiteSpace = data.indices.all {
            data[it].isWhitespace()
        }

        val isValid = hasLengthGreaterThan4 && !containsWhiteSpace

        if (isValid)
            return ValidationResult(isValid = true)

        return ValidationResult(
            isValid = false,
            error = "Username cannot have less than 5 character and cannot contain whitespace"
        )
    }
}