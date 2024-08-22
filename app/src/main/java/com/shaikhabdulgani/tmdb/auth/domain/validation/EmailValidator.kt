package com.shaikhabdulgani.tmdb.auth.domain.validation

class EmailValidator : Validator<String> {
    override fun validate(data: String): ValidationResult {
        val isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(data).matches()
        if (isValid)
            return ValidationResult(isValid = true)
        return ValidationResult(isValid = false, error = "Invalid email address.")
    }
}