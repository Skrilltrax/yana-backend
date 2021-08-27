package dev.skrilltrax.routes.v1

import dev.skrilltrax.db.model.UserModel
import dev.skrilltrax.exception.UserException
import dev.skrilltrax.exception.ValidationException
import dev.skrilltrax.services.UserService
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

private fun Route.login(userService: UserService) {
    route("login") {
        post {
            try {
                val user = call.receive<UserModel>()
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
                val user = call.receive<UserModel>()
                val token = userService.createUser(user.username, user.password)
                call.respond(hashMapOf("token" to token))
            } catch (exception: UserException) {
                call.respond(exception.errorCode, exception.error)
            } catch (exception: ValidationException) {
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