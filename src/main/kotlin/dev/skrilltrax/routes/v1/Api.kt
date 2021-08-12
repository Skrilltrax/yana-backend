package dev.skrilltrax.routes.v1

import io.ktor.routing.*

fun Route.api() {
    route("/api") {
        v1()
    }
}

fun Route.v1() {
    route("/v1") {
        user()
    }
}