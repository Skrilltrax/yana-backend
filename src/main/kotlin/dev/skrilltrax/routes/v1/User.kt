package dev.skrilltrax.routes.v1

import dev.skrilltrax.db.model.UserRequest
import dev.skrilltrax.exception.UserException
import dev.skrilltrax.services.UserService
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

private fun Route.login(userService: UserService) {
    route("login") {
        post {
            try {
                val user = call.receive<UserRequest>()
                val token = userService.authenticateUser(user.username, user.password)
                call.respond(hashMapOf("token" to token))
            } catch (exception: UserException) {
                call.respond(exception.errorCode, exception.error)
            }
        }
    }
}

private fun Route.signup(userService: UserService) {
    route("signup") {
        post {
            try {
                val user = call.receive<UserRequest>()
                val token = userService.createUser(user.username, user.password)
                call.respond(hashMapOf("token" to token))
            } catch (exception: UserException) {
                call.respond(exception.errorCode, exception.error)
            }
        }
    }
}

fun Route.user(userService: UserService) {
    route("/user") {
        login(userService)
        signup(userService)
    }
}