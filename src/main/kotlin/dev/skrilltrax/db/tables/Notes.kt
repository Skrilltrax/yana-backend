package dev.skrilltrax.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object Notes: IntIdTable() {
    val user = reference("user", Users)
    val title = varchar("title", 256)
    val body = text("body")
}