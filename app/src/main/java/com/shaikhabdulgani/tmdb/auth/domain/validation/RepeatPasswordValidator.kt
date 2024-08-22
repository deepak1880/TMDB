package com.shaikhabdulgani.tmdb.auth.domain.validation

class RepeatPasswordValidator : Validator<Pair<String, String>> {
    override fun validate(data: Pair<String, String>): ValidationResult {
        val isValid = data.first == data.second

        if (isValid)
            return ValidationResult(isValid = true)

        return ValidationResult(
            isValid = false,
            error = "Password doesn't match"
        )
    }
}