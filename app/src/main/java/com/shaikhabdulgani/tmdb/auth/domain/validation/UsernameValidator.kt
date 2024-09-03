package com.shaikhabdulgani.tmdb.auth.domain.validation

class UsernameValidator : Validator<String> {
    override fun validate(data: String): ValidationResult {
        // Check if the length is greater than 4
        val hasLengthGreaterThan4 = data.length > 4

        // Check if the username contains only letters and numbers
        val containsOnlyLettersAndNumbers = data.all { it.isLetterOrDigit() }

        // Determine if the username is valid based on the criteria
        val isValid = hasLengthGreaterThan4 && containsOnlyLettersAndNumbers

        if (isValid)
            return ValidationResult(isValid = true)

        return ValidationResult(
            isValid = false,
            error = "Username must be more than 4 characters long and can only contain letters and numbers."
        )
    }
}