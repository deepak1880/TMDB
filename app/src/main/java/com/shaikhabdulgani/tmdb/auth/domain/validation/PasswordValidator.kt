package com.shaikhabdulgani.tmdb.auth.domain.validation

class PasswordValidator : Validator<String> {
    override fun validate(data: String): ValidationResult {
        val hasLength = data.length >= 8
        var containsLowercase = false
        var containsNumber = false
        var containsUppercase = false
        var containsSpecial = false

        for (c in data.toCharArray()) {
            if (c.isDigit()) {
                containsNumber = true
            } else if (c.isUpperCase()) {
                containsUppercase = true
            } else if (c.isLowerCase()) {
                containsLowercase = true
            } else if (!c.isWhitespace()) {
                containsSpecial = true
            }
        }
        val isValid = hasLength && containsLowercase && containsUppercase && containsNumber && containsSpecial

        if (isValid)
            return ValidationResult(isValid = true)

        return ValidationResult(
            isValid = false,
            error = "Password should contain at least one uppercase, one lowercase, one number and one special character"
        )
    }
}