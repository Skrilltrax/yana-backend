package dev.skrilltrax.services

import at.favre.lib.crypto.bcrypt.BCrypt
import dev.skrilltrax.db.dao.UserDao
import dev.skrilltrax.db.tables.UsersTable
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
        if (!UsersTable.exists(username)) throw UserException.UserDoesNotExistException

        val userDao = transaction(database) { UserDao.find { (UsersTable.username eq username) }.single() }

        val result = BCrypt.verifyer().verify(password.toCharArray(), userDao.passwordHash)
        if (!result.verified) throw UserException.InvalidCredentialsException

        return@withContext createToken(dotenv, userDao.userName!!)
    }

    suspend fun createUser(user: String, password: String): String = withContext(Dispatchers.IO) {
        if (UsersTable.exists(user)) throw UserException.UserExistsException

        val hashedPassword = BCrypt.withDefaults().hash(HASH_ROUNDS, password.toCharArray()).decodeToString()

        transaction(database) {
            UserDao.new {
                userName = user
                passwordHash = hashedPassword
            }
        }

        return@withContext createToken(dotenv, user)
    }

    suspend fun checkIfUserExists(user: String): Boolean = UsersTable.exists(user)

    private suspend fun UsersTable.exists(username: String): Boolean = withContext(Dispatchers.IO) {
        return@withContext transaction { UsersTable.select { UsersTable.username eq username }.count() != 0L }
    }

    companion object {
        private const val HASH_ROUNDS = 12
    }
}