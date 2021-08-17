package dev.skrilltrax.db.dao

import dev.skrilltrax.db.model.UserModel
import dev.skrilltrax.db.tables.NotesTable
import dev.skrilltrax.db.tables.UsersTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserDao(id: EntityID<Int>) : IntEntity(id), BaseDao<UserModel> {
    companion object : IntEntityClass<UserDao>(UsersTable)

    var userName by UsersTable.username
    var passwordHash by UsersTable.passwordHash
    val notes by NoteDao referrersOn NotesTable.user

    override fun mapToModel(): UserModel {
        return UserModel(id.value, userName, passwordHash)
    }
}