package dev.skrilltrax.exception

import io.ktor.http.*

class BadRequestException(
    error: String = "Bad Request",
    errorCode: HttpStatusCode = HttpStatusCode.BadRequest
) : BaseException(error, errorCode)