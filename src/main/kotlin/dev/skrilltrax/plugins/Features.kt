package dev.skrilltrax.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import dev.skrilltrax.di.applicationModule
import io.github.cdimascio.dotenv.Dotenv
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.serialization.*
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import org.koin.logger.SLF4JLogger

fun Application.installFeatures() {
    // Ktor features
    install(DefaultHeaders)
    install(CallLogging)

    // JSON
    install(ContentNegotiation) {
        json()
    }

    // Koin
    install(Koin) {
        SLF4JLogger()
        modules(applicationModule)
    }

    // JWT
    val dotenv by inject<Dotenv>()
    val aud = dotenv["JWT_AUDIENCE"]
    val iss = dotenv["JWT_ISSUER"]
    val sec = dotenv["JWT_SECRET"]

    install(Authentication) {
        jwt("auth-user") {
            verifier(
                JWT.require(Algorithm.HMAC256(sec))
                    .withAudience(aud)
                    .withIssuer(iss)
                    .build()
            )

            validate { jwtCredential ->
                if (jwtCredential.payload.getClaim("username").asString() != "") {
                    JWTPrincipal(jwtCredential.payload)
                } else {
                    null
                }
            }
        }
    }
}