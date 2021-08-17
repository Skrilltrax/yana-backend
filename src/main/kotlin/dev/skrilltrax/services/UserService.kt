package dev.skrilltrax.services

import at.favre.lib.crypto.bcrypt.BCrypt
import dev.skrilltrax.db.dao.User
import dev.skrilltrax.db.tables.Users
import dev.skrilltrax.exception.UserException
import dev.skrilltrax.utils.createToken
import io.github.cdimascio.dotenv.Dotenv
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class UserService(private val database: Database, private val dotenv: Dotenv) {

    suspend fun authenticateUser(username: String, password: String): String = withContext(Dispatchers.IO) {
        if (!Users.exists(username)) throw UserException.UserDoesNotExistException

        val user = transaction(database) { User.find { (Users.username eq username) }.single() }

        val result = BCrypt.verifyer().verify(password.toCharArray(), user.passwordHash)
        if (!result.verified) throw UserException.InvalidCredentialsException

        return@withContext createToken(dotenv, user.userName)
    }

    suspend fun createUser(user: String, password: String): String = withContext(Dispatchers.IO) {
        if (Users.exists(user)) throw UserException.UserExistsException

        val hashedPassword = BCrypt.withDefaults().hash(HASH_ROUNDS, password.toCharArray()).decodeToString()

        transaction(database) {
            User.new {
                userName = user
                passwordHash = hashedPassword
            }
        }

        return@withContext createToken(dotenv, user)
    }

    suspend fun checkIfUserExists(user: String): Boolean = Users.exists(user)

    private suspend fun Users.exists(username: String): Boolean = withContext(Dispatchers.IO) {
        return@withContext transaction { Users.select { Users.username eq username }.count() != 0L }
    }

    companion object {
        private const val HASH_ROUNDS = 12
    }
}