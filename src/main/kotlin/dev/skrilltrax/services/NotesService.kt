package dev.skrilltrax.services

import dev.skrilltrax.db.dao.NoteDao
import dev.skrilltrax.db.dao.UserDao
import dev.skrilltrax.db.model.NoteModel
import dev.skrilltrax.db.tables.NotesTable
import dev.skrilltrax.db.tables.UsersTable
import dev.skrilltrax.exception.NoteException
import dev.skrilltrax.exception.UserException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

class NotesService(private val database: Database) {

    suspend fun addNote(noteTitle: String, noteBody: String, noteUserName: String): NoteModel = withContext(Dispatchers.IO) {
        val noteUserDao = transaction(database) { UserDao.find { UsersTable.username eq noteUserName }.singleOrNull() }

        noteUserDao ?: throw UserException.UserDoesNotExistException

        val noteDao = transaction(database) {
            NoteDao.new {
                title = noteTitle
                body = noteBody
                user = noteUserDao.id
            }
        }

        return@withContext noteDao.mapToModel()
    }

    suspend fun deleteNote(id: Int) = withContext(Dispatchers.IO) {
        transaction(database) {
            val noteDao = NoteDao.find { NotesTable.id eq id }.singleOrNull()
            noteDao ?: throw NoteException.NoteDoesNotExistException
            noteDao.delete()
        }
    }

    suspend fun updateNote(id: Int, noteTitle: String, noteBody: String): NoteModel = withContext(Dispatchers.IO) {
        transaction(database) {
            val noteDao = NoteDao.find { NotesTable.id eq id }.singleOrNull()
            noteDao ?: throw NoteException.NoteDoesNotExistException

            noteDao.title = noteTitle
            noteDao.body = noteBody
        }

        return@withContext transaction(database) { NoteDao.find { NotesTable.id eq id }.single() }.mapToModel()
    }

    suspend fun getNote(id: Int): NoteModel = withContext(Dispatchers.IO) {
        return@withContext transaction(database) {
            NoteDao.find { NotesTable.id eq id }.singleOrNull() ?: throw NoteException.NoteDoesNotExistException
        }.mapToModel()
    }

    suspend fun getAllNotes(): List<NoteModel> = withContext(Dispatchers.IO) {
        return@withContext transaction(database) { NoteDao.all().map { it.mapToModel() }.toList() }
    }
}