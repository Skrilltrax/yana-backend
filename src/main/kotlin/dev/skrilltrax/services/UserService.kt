package dev.skrilltrax.services

import at.favre.lib.crypto.bcrypt.BCrypt
import dev.skrilltrax.db.dao.User
import dev.skrilltrax.db.dbQuery
import dev.skrilltrax.db.tables.Users
import dev.skrilltrax.utils.createToken
import io.github.cdimascio.dotenv.Dotenv
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class UserService(private val database: Database, private val dotenv: Dotenv) {

    suspend fun createUser(user: String, password: String): String = withContext(Dispatchers.IO) {
        val hashedPassword = BCrypt.withDefaults().hash(HASH_ROUNDS, password.toCharArray())

        if (Users.exists(user)) error("User already exists")

        transaction(database) {
            Users.insert { column ->
                column[username] = user
                column[passwordHash] = hashedPassword.decodeToString()
            }
        }

        return@withContext createToken(dotenv, user)
    }

    suspend fun checkIfUserExists(user: String): Boolean = Users.exists(user)

    private suspend fun Users.exists(username: String): Boolean = withContext(Dispatchers.IO) {
        return@withContext transaction { Users.select {Users.username eq username}.count() != 0L }
    }

    companion object {
        private const val HASH_ROUNDS = 16
    }
}