package dev.skrilltrax.validators

import dev.skrilltrax.exception.ValidationException

class PasswordValidator(
    private val minLength: Int = 8,
    private val checkLowerCase: Boolean = true,
    private val checkUpperCase: Boolean = true,
    private val checkNumber: Boolean = true,
    private val checkSpecialCharacters: Boolean = true,
) {
    private val lower = "[a-z]"
    private val upper = "[A-Z]"
    private val number = "[0-9]"
    private val special = "[^A-Za-z0-9]"

    @Throws(ValidationException.InvalidPasswordException::class)
    fun validatePassword(password: String) {
        if (password.length < minLength) {
            throw ValidationException.InvalidPasswordException("Password is too short")
        }

        if (checkLowerCase && !password.contains(Regex(lower))) {
            throw ValidationException.InvalidPasswordException("Password should contain at least 1 lowercase character")
        }

        if (checkUpperCase && !password.contains(Regex(upper))) {
            throw ValidationException.InvalidPasswordException("Password should contain at least 1 uppercase character")
        }

        if (checkNumber && !password.contains(Regex(number))) {
            throw ValidationException.InvalidPasswordException("Password should contain at least 1 number")
        }

        if (checkSpecialCharacters && !password.contains(Regex(special))) {
            throw ValidationException.InvalidPasswordException("Password should contain at least 1 special character")
        }
    }
}