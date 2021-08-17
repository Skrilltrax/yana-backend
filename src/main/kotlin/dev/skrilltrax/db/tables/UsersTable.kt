package dev.skrilltrax.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object UsersTable: IntIdTable() {
    val username = varchar("username", 50).index()
    val passwordHash = text("password_hash")
}