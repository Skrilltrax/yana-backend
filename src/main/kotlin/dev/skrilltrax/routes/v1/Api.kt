package dev.skrilltrax.routes.v1

import dev.skrilltrax.services.UserService
import io.ktor.routing.*
import org.koin.java.KoinJavaComponent.inject
import org.koin.ktor.ext.inject

fun Route.api() {
    route("/api") {
        v1()
    }
}

fun Route.v1() {
    val userService by inject<UserService>()

    route("/v1") {
        user(userService)
    }
}