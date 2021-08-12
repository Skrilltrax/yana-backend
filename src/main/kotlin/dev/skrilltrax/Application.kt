package dev.skrilltrax

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import dev.skrilltrax.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        installFeatures()
        configureRouting()
    }.start(wait = true)
}
