package dev.skrilltrax.routes.v1

import dev.skrilltrax.db.model.NoteModel
import dev.skrilltrax.exception.NoteException
import dev.skrilltrax.services.NotesService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

private fun Route.addNote(notesService: NotesService) {
    post {
        try {
            val noteModel = call.receive<NoteModel>()
            val principal = call.principal<JWTPrincipal>()
            val username = principal?.get("username") ?: TODO()

            val note = notesService.addNote(noteModel.title, noteModel.body, username)
            call.respond(note)
        } catch (exception: NoteException) {
            call.respond(exception.errorCode, exception.error)
        }
    }
}

private fun Route.deleteNote(notesService: NotesService) {
    delete("{id}") {
        try {
            val id = call.parameters["id"]?.toIntOrNull() ?: TODO()

            val note = notesService.deleteNote(id)
            call.respond(note)
        } catch (exception: NoteException) {
            call.respond(exception.errorCode, exception.error)
        }
    }
}

private fun Route.updateNote(notesService: NotesService) {
    put {
        try {
            val noteModel = call.receive<NoteModel>()
            val principal = call.principal<JWTPrincipal>()
            val id = noteModel.id ?: TODO()

            val note = notesService.updateNote(id, noteModel.title, noteModel.body)
            call.respond(note)
        } catch (exception: NoteException) {
            call.respond(exception.errorCode, exception.error)
        }
    }
}

private fun Route.getNote(notesService: NotesService) {
    get("{id}") {
        try {
            val id = call.parameters["id"]?.toIntOrNull() ?: TODO()

            val note = notesService.getNote(id)
            call.respond(note)
        } catch (exception: NoteException) {
            call.respond(exception.errorCode, exception.error)
        }
    }
}

fun Route.notes(notesService: NotesService) {
    route("notes") {
        authenticate("auth-jwt") {
            addNote(notesService)
            deleteNote(notesService)
            updateNote(notesService)
            getNote(notesService)
        }
    }
}