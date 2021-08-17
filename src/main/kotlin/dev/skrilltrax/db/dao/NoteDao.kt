package dev.skrilltrax.db.dao

import dev.skrilltrax.db.model.NoteModel
import dev.skrilltrax.db.tables.NotesTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class NoteDao(id: EntityID<Int>): IntEntity(id), BaseDao<NoteModel> {
    companion object : IntEntityClass<NoteDao>(NotesTable)

    var title by NotesTable.title
    var body by NotesTable.body
    var user by NotesTable.user

    override fun mapToModel(): NoteModel {
        return NoteModel(id.value, title, body)
    }
}