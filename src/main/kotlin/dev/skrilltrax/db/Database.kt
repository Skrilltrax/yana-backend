package dev.skrilltrax.db

import io.github.cdimascio.dotenv.Dotenv
import org.jetbrains.exposed.sql.Database

fun database(dotenv: Dotenv): Database = Database.connect(
    "jdbc:mysql://localhost:3306/test",
    driver = "com.mysql.jdbc.Driver",
    user = dotenv["DB_USERNAME"],
    password = dotenv["DB_PASSWORD"]
)