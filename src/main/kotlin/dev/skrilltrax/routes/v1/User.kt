package dev.skrilltrax.routes.v1

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

private fun Route.login() {
    route("login") {
        get {
            call.respondText("Hello World!")
        }
    }
}

private fun Route.signup() {
    route("signup") {
        get {

        }
        post {

        }
    }
}

fun Route.user() {
    route("/user") {
        login()
        signup()
    }
}