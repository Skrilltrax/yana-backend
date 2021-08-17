package dev.skrilltrax.utils

import dev.skrilltrax.exception.BaseException
import io.ktor.application.*
import io.ktor.response.*

suspend fun ApplicationCall.respond(exception: BaseException) {
    respond(exception.errorCode, exception.error)
}