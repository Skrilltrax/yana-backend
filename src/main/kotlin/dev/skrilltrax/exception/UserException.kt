package dev.skrilltrax.exception

import io.ktor.http.*

sealed class UserException(error: String, errorCode: HttpStatusCode): BaseException(error, errorCode) {
    object UserExistsException: UserException("User already exists", HttpStatusCode.Conflict)
    object UserDoesNotExistException: UserException("User does not exist", HttpStatusCode.Unauthorized)
    object InvalidCredentialsException: UserException("Invalid credentials", HttpStatusCode.Unauthorized)
}
