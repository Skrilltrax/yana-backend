package dev.skrilltrax.db.dao

import dev.skrilltrax.db.tables.Notes
import dev.skrilltrax.db.tables.Users
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Note(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<Note>(Users)

    var title by Notes.title
    var body by Notes.body
    var user by User referencedOn Notes.user
}