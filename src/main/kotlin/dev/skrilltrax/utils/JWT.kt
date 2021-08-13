package dev.skrilltrax.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.github.cdimascio.dotenv.Dotenv

fun createToken(dotenv: Dotenv, username: String): String {
    val aud = dotenv["JWT_AUDIENCE"]
    val iss = dotenv["JWT_ISSUER"]
    val sec = dotenv["JWT_SECRET"]

    val token = JWT.create()
        .withAudience(aud)
        .withIssuer(iss)
        .withClaim("username", username)
        .sign(Algorithm.HMAC256(sec))

    return token ?: error("Error creating JWT token, token received from JWT.create() was null")
}
