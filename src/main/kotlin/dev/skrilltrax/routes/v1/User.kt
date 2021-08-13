package dev.skrilltrax.routes.v1

import dev.skrilltrax.db.model.UserRequest
import dev.skrilltrax.exception.BaseException
import dev.skrilltrax.services.UserService
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

private fun Route.login() {
    route("login") {
        get {
            call.respondText("Hello World!")
        }
    }
}

private fun Route.signup(userService: UserService) {
    route("signup") {
        post {
            val user = call.receive<UserRequest>()
            try {
                val token = userService.createUser(user.username, user.password)
            } catch (exception: BaseException) {
                respond
            }

            call.respond(hashMapOf("token" to token))
        }
    }
}

fun Route.user(userService: UserService) {
    route("/user") {
        login()
        signup(userService)
    }
}