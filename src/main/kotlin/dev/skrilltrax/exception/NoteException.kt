package dev.skrilltrax.exception

import io.ktor.http.*

sealed class NoteException(error: String, errorCode: HttpStatusCode) : BaseException(error, errorCode) {
    object NoteDoesNotExistException : NoteException("Note does not exist", HttpStatusCode.NotFound)
}
