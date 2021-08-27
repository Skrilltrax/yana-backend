package dev.skrilltrax.exception

import io.ktor.http.*

sealed class ValidationException(error: String, errorCode: HttpStatusCode) : BaseException(error, errorCode) {
    class InvalidUserNameException(status: String) : ValidationException("Invalid Username: $status", HttpStatusCode.BadRequest)
    class InvalidPasswordException(status: String) : ValidationException("Invalid Password: $status", HttpStatusCode.BadRequest)
}