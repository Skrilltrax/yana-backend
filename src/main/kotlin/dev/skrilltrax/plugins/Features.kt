package dev.skrilltrax.plugins

import dev.skrilltrax.di.applicationModule
import io.ktor.application.*
import io.ktor.features.*
import org.koin.ktor.ext.Koin
import org.koin.logger.SLF4JLogger

fun Application.installFeatures() {
    // Ktor features
    install(DefaultHeaders)
    install(CallLogging)

    // Koin
    install(Koin) {
        SLF4JLogger()
        modules(applicationModule)
    }
}