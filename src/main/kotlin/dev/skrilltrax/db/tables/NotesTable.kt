package dev.skrilltrax.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object NotesTable: IntIdTable() {
    val user = reference("user", UsersTable)
    val title = varchar("title", 256)
    val body = text("body")
}