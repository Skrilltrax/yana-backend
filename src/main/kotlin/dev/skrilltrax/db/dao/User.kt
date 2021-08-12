package dev.skrilltrax.db.dao

import dev.skrilltrax.db.tables.Users
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class User(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<User>(Users)

    var userName by Users.username
    var passwordHash by Users.passwordHash
}