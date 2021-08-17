package dev.skrilltrax.exception

import io.ktor.http.*

open class BaseException(val error: String, val errorCode: HttpStatusCode, cause: Throwable? = null) : Exception(error, cause)