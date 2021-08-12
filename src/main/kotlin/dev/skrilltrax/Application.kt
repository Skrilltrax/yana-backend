package dev.skrilltrax

import dev.skrilltrax.db.initializeDatabase
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import dev.skrilltrax.plugins.*
import org.koin.ktor.ext.get

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        installFeatures()
        initializeDatabase(get())
        configureRouting()
    }.start(wait = true)
}
