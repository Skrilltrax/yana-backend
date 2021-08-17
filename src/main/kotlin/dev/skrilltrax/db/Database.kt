package dev.skrilltrax.db

import dev.skrilltrax.db.tables.NotesTable
import dev.skrilltrax.db.tables.UsersTable
import io.github.cdimascio.dotenv.Dotenv
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun database(dotenv: Dotenv): Database = Database.connect(
    "jdbc:mysql://localhost:3306/test",
    driver = "com.mysql.jdbc.Driver",
    user = dotenv["DB_USERNAME"],
    password = dotenv["DB_PASSWORD"]
)

fun initializeDatabase(database: Database) {
    transaction(database) {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(UsersTable, NotesTable)
    }
}

suspend fun <T> dbQuery(block: () -> T): T = withContext(Dispatchers.IO) {
    transaction { block() }
}
