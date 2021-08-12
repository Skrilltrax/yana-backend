package dev.skrilltrax.plugins

import dev.skrilltrax.routes.v1.api
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import org.koin.ktor.ext.get

fun Application.configureRouting() {

    routing {
        route("/") {
            api()
        }
    }

    routing {
        get("/hello") {
            call.respondText("Hello World!")
        }
    }
}
